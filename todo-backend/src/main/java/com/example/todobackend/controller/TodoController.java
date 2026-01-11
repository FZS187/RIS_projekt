package com.example.todobackend.controller;

import com.example.todobackend.dto.TodoStatisticsDTO;
import com.example.todobackend.dto.TodoWithSyncDTO;
import com.example.todobackend.model.Category;
import com.example.todobackend.model.Priority;
import com.example.todobackend.model.TaskSyncStatus;
import com.example.todobackend.model.Todo;
import com.example.todobackend.service.TodoService;
import com.example.todobackend.service.TaskSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller za upravljanje nalog
 * Definira API endpointe za frontend
 *
 * TASK-2: Dodan /statistics endpoint za analizo produktivnosti
 * NOVA FUNKCIONALNOST: Dodana sinhronizacija nalog sa statusima
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

    // ========== OSNOVNI ENDPOINTI (sa sync statusom) ==========

    /**
     * GET /api/todos - Dobi sve naloge SA sync statusima
     */
    @GetMapping
    public ResponseEntity<List<TodoWithSyncDTO>> getAllTodos() {
        List<Todo> todos = todoService.getAllTodos();

        List<TodoWithSyncDTO> todosWithSync = todos.stream()
                .map(todo -> {
                    TaskSyncStatus syncStatus = syncService.getOrCreateSyncStatus(todo);
                    return new TodoWithSyncDTO(todo, syncStatus);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(todosWithSync);
    }

    /**
     * GET /api/todos/{id} - Dobi nalogo po ID-ju SA sync statusom
     */
    @GetMapping("/{id}")
    public ResponseEntity<TodoWithSyncDTO> getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id)
                .map(todo -> {
                    TaskSyncStatus syncStatus = syncService.getOrCreateSyncStatus(todo);
                    return ResponseEntity.ok(new TodoWithSyncDTO(todo, syncStatus));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/todos - Ustvari novo nalogo in avtomatsko začni sinhronizacijo
     */
    @PostMapping
    public ResponseEntity<TodoWithSyncDTO> createTodo(@RequestBody Todo todo) {
        Todo savedTodo = todoService.createTodo(todo);

        // ✅ AUTOMATSKI POSTAVI STATUS NA "V TEKU"
        TaskSyncStatus syncStatus = syncService.startSync(savedTodo);

        // Simuliraj sinhronizaciju u pozadini (asinhrono)
        new Thread(() -> {
            syncService.simulateSync(savedTodo);
        }).start();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new TodoWithSyncDTO(savedTodo, syncStatus));
    }

    /**
     * PUT /api/todos/{id} - Posodobi obstoječo nalogo i pokreni re-sinhronizaciju
     */
    @PutMapping("/{id}")
    public ResponseEntity<TodoWithSyncDTO> updateTodo(@PathVariable Long id, @RequestBody Todo todoDetails) {
        try {
            Todo updatedTodo = todoService.updateTodo(id, todoDetails);

            // ✅ POKRENI RE-SINHRONIZACIJU NAKON IZMENE
            TaskSyncStatus syncStatus = syncService.startSync(updatedTodo);

            new Thread(() -> {
                syncService.simulateSync(updatedTodo);
            }).start();

            return ResponseEntity.ok(new TodoWithSyncDTO(updatedTodo, syncStatus));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/todos/{id} - Izbriši nalogo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * PATCH /api/todos/{id}/toggle - Preklopi status dokončanosti
     */
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<TodoWithSyncDTO> toggleComplete(@PathVariable Long id) {
        try {
            Todo toggledTodo = todoService.toggleComplete(id);

            // Pokreni sinhronizaciju nakon promene statusa
            TaskSyncStatus syncStatus = syncService.startSync(toggledTodo);

            new Thread(() -> {
                syncService.simulateSync(toggledTodo);
            }).start();

            return ResponseEntity.ok(new TodoWithSyncDTO(toggledTodo, syncStatus));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ========== SEARCH I FILTER ENDPOINTI ==========

    /**
     * GET /api/todos/search?keyword=xyz - Išči naloge po imenu
     */
    @GetMapping("/search")
    public ResponseEntity<List<TodoWithSyncDTO>> searchTodos(@RequestParam String keyword) {
        List<Todo> todos = todoService.searchByName(keyword);

        List<TodoWithSyncDTO> todosWithSync = todos.stream()
                .map(todo -> {
                    TaskSyncStatus syncStatus = syncService.getOrCreateSyncStatus(todo);
                    return new TodoWithSyncDTO(todo, syncStatus);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(todosWithSync);
    }

    /**
     * GET /api/todos/filter?completed=true - Filtriraj po statusu
     */
    @GetMapping("/filter")
    public ResponseEntity<List<TodoWithSyncDTO>> filterTodos(@RequestParam boolean completed) {
        List<Todo> todos = todoService.filterByCompleted(completed);

        List<TodoWithSyncDTO> todosWithSync = todos.stream()
                .map(todo -> {
                    TaskSyncStatus syncStatus = syncService.getOrCreateSyncStatus(todo);
                    return new TodoWithSyncDTO(todo, syncStatus);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(todosWithSync);
    }

    // ========== SYNC STATUS ENDPOINTI ==========

    /**
     * GET /api/todos/{id}/sync-status - Dobi samo sync status za določeno nalogo
     */
    @GetMapping("/{id}/sync-status")
    public ResponseEntity<TaskSyncStatus> getSyncStatus(@PathVariable Long id) {
        return syncService.getSyncStatus(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/todos/{id}/sync - Ročno pokreni sinhronizacijo za določeno nalogo
     */
    @PostMapping("/{id}/sync")
    public ResponseEntity<TodoWithSyncDTO> triggerSync(@PathVariable Long id) {
        return todoService.getTodoById(id)
                .map(todo -> {
                    TaskSyncStatus syncStatus = syncService.startSync(todo);

                    new Thread(() -> {
                        syncService.simulateSync(todo);
                    }).start();

                    return ResponseEntity.ok(new TodoWithSyncDTO(todo, syncStatus));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/todos/syncing - Dobi vse naloge koje su trenutno u sinhronizaciji
     */
    @GetMapping("/syncing")
    public ResponseEntity<List<TaskSyncStatus>> getTasksInProgress() {
        List<TaskSyncStatus> inProgressTasks = syncService.getTasksInProgress();
        return ResponseEntity.ok(inProgressTasks);
    }

    // ========== STATISTIKA ENDPOINT ==========

    /**
     * TASK-2: GET /api/todos/statistics - Dobi statistiko nalog
     *
     * Vrne celotno analizo produktivnosti:
     * - Skupno število nalog
     * - Dokončane/nedokončane naloge
     * - Odstotek dokončanosti
     * - Razdelitev po kategorijah
     * - Razdelitev po prioritetah
     * - Pretečene naloge
     *
     * Primer klica: GET http://localhost:8080/api/todos/statistics
     *
     * @return TodoStatisticsDTO z vso statistiko
     */
    @GetMapping("/statistics")
    public ResponseEntity<TodoStatisticsDTO> getStatistics() {
        try {
            TodoStatisticsDTO statistics = todoService.getStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            // Log napake (v produkciji uporabi logger)
            System.err.println("❌ Napaka pri pridobivanju statistike: " + e.getMessage());
            e.printStackTrace();

            // Vrni prazno statistiko v primeru napake
            TodoStatisticsDTO emptyStats = new TodoStatisticsDTO();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(emptyStats);
        }
    }

    // ========== CATEGORY I PRIORITY ENDPOINTI ==========

    /**
     * GET /api/todos/category/{category} - Dobi naloge po kategoriji
     * Primer: GET /api/todos/category/WORK
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<TodoWithSyncDTO>> getTodosByCategory(@PathVariable String category) {
        try {
            Category cat = Category.valueOf(category.toUpperCase());
            List<Todo> todos = todoService.getTodosByCategory(cat);

            List<TodoWithSyncDTO> todosWithSync = todos.stream()
                    .map(todo -> {
                        TaskSyncStatus syncStatus = syncService.getOrCreateSyncStatus(todo);
                        return new TodoWithSyncDTO(todo, syncStatus);
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(todosWithSync);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET /api/todos/priority/{priority} - Dobi naloge po prioriteti
     * Primer: GET /api/todos/priority/HIGH
     */
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TodoWithSyncDTO>> getTodosByPriority(@PathVariable String priority) {
        try {
            Priority pri = Priority.valueOf(priority.toUpperCase());
            List<Todo> todos = todoService.getTodosByPriority(pri);

            List<TodoWithSyncDTO> todosWithSync = todos.stream()
                    .map(todo -> {
                        TaskSyncStatus syncStatus = syncService.getOrCreateSyncStatus(todo);
                        return new TodoWithSyncDTO(todo, syncStatus);
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(todosWithSync);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET /api/todos/high-priority - Dobi visoko prioritetne nedokončane naloge
     */
    @GetMapping("/high-priority")
    public ResponseEntity<List<TodoWithSyncDTO>> getHighPriorityTasks() {
        List<Todo> todos = todoService.getHighPriorityIncompleteTasks();

        List<TodoWithSyncDTO> todosWithSync = todos.stream()
                .map(todo -> {
                    TaskSyncStatus syncStatus = syncService.getOrCreateSyncStatus(todo);
                    return new TodoWithSyncDTO(todo, syncStatus);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(todosWithSync);
    }

    /**
     * GET /api/todos/categories - Dobi seznam kategorij
     */
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        List<String> categories = java.util.Arrays.stream(Category.values())
                .map(Enum::name)
                .toList();
        return ResponseEntity.ok(categories);
    }

    /**
     * GET /api/todos/priorities - Dobi seznam prioritet
     */
    @GetMapping("/priorities")
    public ResponseEntity<List<String>> getPriorities() {
        List<String> priorities = java.util.Arrays.stream(Priority.values())
                .map(Enum::name)
                .toList();
        return ResponseEntity.ok(priorities);
    }
}