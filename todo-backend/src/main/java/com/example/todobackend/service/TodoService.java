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

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    public Todo saveTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    public Optional<Todo> updateTodo(Long id, Todo todoDetails) {
        return todoRepository.findById(id).map(todo -> {
            todo.setName(todoDetails.getName());
            todo.setCompleted(todoDetails.isCompleted());
            todo.setDueDate(todoDetails.getDueDate());
            todo.setReminderAt(todoDetails.getReminderAt());
            return todoRepository.save(todo);
        });
    }

    public List<Todo> getFilteredTodos(String search, String status) {
        if (status != null && !status.equalsIgnoreCase("all")) {
            boolean isCompleted = status.equalsIgnoreCase("completed");
            if (search != null && !search.isEmpty()) {
                return todoRepository.findByNameContainingIgnoreCaseAndCompleted(search, isCompleted);
            } else {
                return todoRepository.findByCompleted(isCompleted);
            }
        }

        if (search != null && !search.isEmpty()) {
            return todoRepository.findByNameContainingIgnoreCase(search);
        }

        return todoRepository.findAll();
    }
}