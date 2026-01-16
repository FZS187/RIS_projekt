package com.example.todobackend.controller;

import com.example.todobackend.dto.TodoStatisticsDTO;
import com.example.todobackend.dto.TodoWithSyncDTO;
import com.example.todobackend.model.*;
import com.example.todobackend.repository.UserRepository;
import com.example.todobackend.service.TodoService;
import com.example.todobackend.service.TaskSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ✅ FIXED: Sada sve metode koriste trenutno ulogovanog korisnika
 */
@RestController
@RequestMapping("/api/todos")
@CrossOrigin(
        origins = {"http://localhost:5173", "http://127.0.0.1:5173"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS},
        allowedHeaders = "*",
        allowCredentials = "true",
        maxAge = 3600
)
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private TaskSyncService syncService;

    @Autowired
    private UserRepository userRepository;

    /**
     * ✅ Helper metoda - Dohvati trenutno ulogovanog korisnika
     */
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            throw new RuntimeException("Korisnik nije prijavljen");
        }

        String email = auth.getName(); // Username je email
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Korisnik nije pronađen"));
    }

    /**
     * ✅ GET /api/todos - Samo Todo-i trenutnog korisnika
     */
    @GetMapping
    public ResponseEntity<List<TodoWithSyncDTO>> getAllTodos() {
        try {
            User currentUser = getCurrentUser();
            List<Todo> todos = todoService.getAllTodos(currentUser);

            List<TodoWithSyncDTO> todosWithSync = todos.stream()
                    .map(todo -> {
                        TaskSyncStatus syncStatus = syncService.getOrCreateSyncStatus(todo);
                        return new TodoWithSyncDTO(todo, syncStatus);
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(todosWithSync);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * ✅ GET /api/todos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TodoWithSyncDTO> getTodoById(@PathVariable Long id) {
        try {
            User currentUser = getCurrentUser();
            return todoService.getTodoById(id, currentUser)
                    .map(todo -> {
                        TaskSyncStatus syncStatus = syncService.getOrCreateSyncStatus(todo);
                        return ResponseEntity.ok(new TodoWithSyncDTO(todo, syncStatus));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * ✅ POST /api/todos - Kreiraj Todo za trenutnog korisnika
     * AUTOMATSKI SINHRONIZUJE SA GOOGLE CALENDAR-OM
     */
    @PostMapping
    public ResponseEntity<TodoWithSyncDTO> createTodo(@RequestBody Todo todo) {
        try {
            User currentUser = getCurrentUser();
            Todo savedTodo = todoService.createTodo(todo, currentUser);

            // ✅ PRAVA GOOGLE CALENDAR SINHRONIZACIJA
            TaskSyncStatus syncStatus = syncService.startSync(savedTodo);

            new Thread(() -> {
                syncService.syncWithGoogleCalendar(savedTodo);
            }).start();

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new TodoWithSyncDTO(savedTodo, syncStatus));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * ✅ PUT /api/todos/{id} - Ažuriraj samo svoj Todo
     * AUTOMATSKI RE-SINHRONIZUJE SA GOOGLE CALENDAR-OM
     */
    @PutMapping("/{id}")
    public ResponseEntity<TodoWithSyncDTO> updateTodo(@PathVariable Long id, @RequestBody Todo todoDetails) {
        try {
            User currentUser = getCurrentUser();
            Todo updatedTodo = todoService.updateTodo(id, todoDetails, currentUser);

            // ✅ RE-SINHRONIZACIJA SA GOOGLE CALENDAR-OM
            TaskSyncStatus syncStatus = syncService.startSync(updatedTodo);

            new Thread(() -> {
                syncService.syncWithGoogleCalendar(updatedTodo);
            }).start();

            return ResponseEntity.ok(new TodoWithSyncDTO(updatedTodo, syncStatus));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * ✅ DELETE /api/todos/{id} - Obriši samo svoj Todo
     * AUTOMATSKI BRIŠE IZ GOOGLE CALENDAR-A
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        try {
            User currentUser = getCurrentUser();

            // Prvo dohvati Todo da bi obrisao iz Google Calendar-a
            Todo todo = todoService.getTodoById(id, currentUser)
                    .orElseThrow(() -> new RuntimeException("Todo nije pronađen"));

            // ✅ OBRIŠI IZ GOOGLE CALENDAR-A
            syncService.deleteGoogleCalendarEvent(todo);

            // Obriši iz baze
            todoService.deleteTodo(id, currentUser);

            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * ✅ PATCH /api/todos/{id}/toggle
     */
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<TodoWithSyncDTO> toggleComplete(@PathVariable Long id) {
        try {
            User currentUser = getCurrentUser();
            Todo toggledTodo = todoService.toggleComplete(id, currentUser);

            // Sinhronizuj promenu statusa
            TaskSyncStatus syncStatus = syncService.startSync(toggledTodo);

            new Thread(() -> {
                syncService.syncWithGoogleCalendar(toggledTodo);
            }).start();

            return ResponseEntity.ok(new TodoWithSyncDTO(toggledTodo, syncStatus));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * ✅ GET /api/todos/search
     */
    @GetMapping("/search")
    public ResponseEntity<List<TodoWithSyncDTO>> searchTodos(@RequestParam String keyword) {
        try {
            User currentUser = getCurrentUser();
            List<Todo> todos = todoService.searchByName(keyword, currentUser);

            List<TodoWithSyncDTO> todosWithSync = todos.stream()
                    .map(todo -> {
                        TaskSyncStatus syncStatus = syncService.getOrCreateSyncStatus(todo);
                        return new TodoWithSyncDTO(todo, syncStatus);
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(todosWithSync);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * ✅ GET /api/todos/filter
     */
    @GetMapping("/filter")
    public ResponseEntity<List<TodoWithSyncDTO>> filterTodos(@RequestParam boolean completed) {
        try {
            User currentUser = getCurrentUser();
            List<Todo> todos = todoService.filterByCompleted(completed, currentUser);

            List<TodoWithSyncDTO> todosWithSync = todos.stream()
                    .map(todo -> {
                        TaskSyncStatus syncStatus = syncService.getOrCreateSyncStatus(todo);
                        return new TodoWithSyncDTO(todo, syncStatus);
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(todosWithSync);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * ✅ POST /api/todos/{id}/sync - Ručno pokreni PRAVU sinhronizaciju
     */
    @PostMapping("/{id}/sync")
    public ResponseEntity<TodoWithSyncDTO> triggerSync(@PathVariable Long id) {
        try {
            User currentUser = getCurrentUser();

            return todoService.getTodoById(id, currentUser)
                    .map(todo -> {
                        // ✅ PRAVA GOOGLE CALENDAR SINHRONIZACIJA
                        TaskSyncStatus syncStatus = syncService.syncWithGoogleCalendar(todo);

                        // Refresh todo iz baze
                        Todo updatedTodo = todoService.getTodoById(id, currentUser).orElse(todo);

                        return ResponseEntity.ok(new TodoWithSyncDTO(updatedTodo, syncStatus));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * ✅ GET /api/todos/statistics - Statistika za trenutnog korisnika
     */
    @GetMapping("/statistics")
    public ResponseEntity<TodoStatisticsDTO> getStatistics() {
        try {
            User currentUser = getCurrentUser();
            TodoStatisticsDTO statistics = todoService.getStatistics(currentUser);
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            System.err.println("❌ Napaka pri pridobivanju statistike: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TodoStatisticsDTO());
        }
    }

    /**
     * ✅ GET /api/todos/category/{category}
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<TodoWithSyncDTO>> getTodosByCategory(@PathVariable String category) {
        try {
            User currentUser = getCurrentUser();
            Category cat = Category.valueOf(category.toUpperCase());
            List<Todo> todos = todoService.getTodosByCategory(cat, currentUser);

            List<TodoWithSyncDTO> todosWithSync = todos.stream()
                    .map(todo -> {
                        TaskSyncStatus syncStatus = syncService.getOrCreateSyncStatus(todo);
                        return new TodoWithSyncDTO(todo, syncStatus);
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(todosWithSync);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * ✅ GET /api/todos/priority/{priority}
     */
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TodoWithSyncDTO>> getTodosByPriority(@PathVariable String priority) {
        try {
            User currentUser = getCurrentUser();
            Priority pri = Priority.valueOf(priority.toUpperCase());
            List<Todo> todos = todoService.getTodosByPriority(pri, currentUser);

            List<TodoWithSyncDTO> todosWithSync = todos.stream()
                    .map(todo -> {
                        TaskSyncStatus syncStatus = syncService.getOrCreateSyncStatus(todo);
                        return new TodoWithSyncDTO(todo, syncStatus);
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(todosWithSync);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        List<String> categories = java.util.Arrays.stream(Category.values())
                .map(Enum::name)
                .toList();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/priorities")
    public ResponseEntity<List<String>> getPriorities() {
        List<String> priorities = java.util.Arrays.stream(Priority.values())
                .map(Enum::name)
                .toList();
        return ResponseEntity.ok(priorities);
    }
}