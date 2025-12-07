package com.example.todobackend.controller;

import com.example.todobackend.model.Todo;
import com.example.todobackend.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class TodoController {

    @Autowired
    private TodoService todoService;

    // 1. GET: Dohvati sve zadatke (MODIFIKOVANO)
    // Dodajemo @RequestParam za opcionalne filtere
    @GetMapping
    public List<Todo> getFilteredTodos(
            @RequestParam(required = false) String search, // Opciona ključna reč
            @RequestParam(required = false) String status  // Opcioni status: "completed", "active", "all"
    ) {
        return todoService.getFilteredTodos(search, status);
    }
    
    // Ostatak CRUD operacija ostaje isti
    // 2. POST: Kreiraj novi zadatak
    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        return todoService.saveTodo(todo);
    }

    // 3. PUT: Ažuriraj (EDIT) zadatak
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo todoDetails) {
        return todoService.updateTodo(id, todoDetails)
                .map(updatedTodo -> ResponseEntity.ok(updatedTodo))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 4. DELETE: Obriši zadatak
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}
