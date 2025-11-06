package com.example.todobackend.service;

import com.example.todobackend.model.Todo;
import com.example.todobackend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // 1. DOBIJANJE SVIH ZADATAKA
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    // 2. DOBIJANJE JEDNOG ZADATKA PO ID-u
    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    // 3. DODAVANJE ZADATKA
    public Todo saveTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    // 4. BRISANJE ZADATKA
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    // 5. AŽURIRANJE ZADATKA (Poslovna logika za UREĐIVANJE)
    public Optional<Todo> updateTodo(Long id, Todo todoDetails) {
        // Tražimo zadatak u bazi
        return todoRepository.findById(id).map(todo -> {

            // Ažuriramo sva polja koja su deo zahteva
            todo.setName(todoDetails.getName());
            todo.setCompleted(todoDetails.isCompleted());
            todo.setDueDate(todoDetails.getDueDate());

            return todoRepository.save(todo); // Čuvamo promenu
        });
    }

    public List<Todo> getFilteredTodos(String search, String status) {

        // 1. Opciono filtriranje po statusu
        if (status != null && !status.equalsIgnoreCase("all")) {
            boolean isCompleted = status.equalsIgnoreCase("completed");

            if (search != null && !search.isEmpty()) {
                // Slučaj: Pretraga po ključnoj reči I statusu
                return todoRepository.findByNameContainingIgnoreCaseAndCompleted(search, isCompleted);
            } else {
                // Slučaj: Samo filtriranje po statusu (bez ključne reči)
                return todoRepository.findByCompleted(isCompleted); // Pretpostavljamo da imamo ovu metodu u Repozitorijumu (ili je dodaj)
            }
        }

        // 2. Opciono filtriranje samo po ključnoj reči
        if (search != null && !search.isEmpty()) {
            return todoRepository.findByNameContainingIgnoreCase(search);
        }

        // 3. Podrazumevani Slučaj: Vrati sve
        return todoRepository.findAll();
    }
}