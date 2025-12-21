package com.example.todobackend.controller;

import com.example.todobackend.dto.TodoStatisticsDTO;
import com.example.todobackend.model.Category;
import com.example.todobackend.model.Priority;
import com.example.todobackend.model.Todo;
import com.example.todobackend.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller za upravljanje nalog
 * Definira API endpointe za frontend
 *
 * TASK-2: Dodan /statistics endpoint za analizo produktivnosti
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

    // ========== OBSTOJECI ENDPOINTI (če že obstajajo) ==========

    /**
     * GET /api/todos - Dobi vse naloge
     */
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        List<Todo> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    /**
     * GET /api/todos/{id} - Dobi nalogo po ID-ju
     */
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/todos - Ustvari novo nalogo
     */
    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        Todo createdTodo = todoService.createTodo(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
    }

    /**
     * PUT /api/todos/{id} - Posodobi obstoječo nalogo
     */
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todoDetails) {
        try {
            Todo updatedTodo = todoService.updateTodo(id, todoDetails);
            return ResponseEntity.ok(updatedTodo);
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
    public ResponseEntity<Todo> toggleComplete(@PathVariable Long id) {
        try {
            Todo toggledTodo = todoService.toggleComplete(id);
            return ResponseEntity.ok(toggledTodo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET /api/todos/search?keyword=xyz - Išči naloge po imenu
     */
    @GetMapping("/search")
    public ResponseEntity<List<Todo>> searchTodos(@RequestParam String keyword) {
        List<Todo> todos = todoService.searchByName(keyword);
        return ResponseEntity.ok(todos);
    }

    /**
     * GET /api/todos/filter?completed=true - Filtriraj po statusu
     */
    @GetMapping("/filter")
    public ResponseEntity<List<Todo>> filterTodos(@RequestParam boolean completed) {
        List<Todo> todos = todoService.filterByCompleted(completed);
        return ResponseEntity.ok(todos);
    }

    // ========== NOVI ENDPOINT ZA TASK-2 - STATISTIKA ==========

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

    // ========== DODATNI ENDPOINTI ZA FILTRIRANJE ==========

    /**
     * GET /api/todos/category/{category} - Dobi naloge po kategoriji
     * Primer: GET /api/todos/category/WORK
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Todo>> getTodosByCategory(@PathVariable String category) {
        try {
            Category cat = Category.valueOf(category.toUpperCase());
            List<Todo> todos = todoService.getTodosByCategory(cat);
            return ResponseEntity.ok(todos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET /api/todos/priority/{priority} - Dobi naloge po prioriteti
     * Primer: GET /api/todos/priority/HIGH
     */
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Todo>> getTodosByPriority(@PathVariable String priority) {
        try {
            Priority pri = Priority.valueOf(priority.toUpperCase());
            List<Todo> todos = todoService.getTodosByPriority(pri);
            return ResponseEntity.ok(todos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET /api/todos/high-priority - Dobi visoko prioritetne nedokončane naloge
     */
    @GetMapping("/high-priority")
    public ResponseEntity<List<Todo>> getHighPriorityTasks() {
        List<Todo> todos = todoService.getHighPriorityIncompleteTasks();
        return ResponseEntity.ok(todos);
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
