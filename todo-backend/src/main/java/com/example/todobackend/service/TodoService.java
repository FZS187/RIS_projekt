package com.example.todobackend.service;

import com.example.todobackend.dto.TodoStatisticsDTO;
import com.example.todobackend.model.Category;
import com.example.todobackend.model.Priority;
import com.example.todobackend.model.Todo;
import com.example.todobackend.model.User;
import com.example.todobackend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * ✅ UPDATED: Sve metode sada rade sa User kontekstom
 */
@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    // ✅ UPDATED: Samo Todo-i trenutnog korisnika
    public List<Todo> getAllTodos(User user) {
        return todoRepository.findByUser(user);
    }

    // ✅ UPDATED: Dohvati Todo samo ako pripada korisniku
    public Optional<Todo> getTodoById(Long id, User user) {
        return todoRepository.findByIdAndUser(id, user);
    }

    // ✅ UPDATED: Kreiraj Todo sa korisnikom
    public Todo createTodo(Todo todo, User user) {
        if (todo.getCategory() == null) {
            todo.setCategory(Category.OTHER);
        }
        if (todo.getPriority() == null) {
            todo.setPriority(Priority.MEDIUM);
        }

        // ✅ Postavi korisnika
        todo.setUser(user);

        return todoRepository.save(todo);
    }

    // ✅ UPDATED: Ažuriraj samo ako pripada korisniku
    public Todo updateTodo(Long id, Todo todoDetails, User user) {
        Todo todo = todoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Naloga nije pronađena ili ne pripada vama"));

        todo.setName(todoDetails.getName());
        todo.setDescription(todoDetails.getDescription());
        todo.setCompleted(todoDetails.isCompleted());
        todo.setDueDate(todoDetails.getDueDate());
        todo.setReminderAt(todoDetails.getReminderAt());
        todo.setCategory(todoDetails.getCategory());
        todo.setPriority(todoDetails.getPriority());

        if (todoDetails.getSyncStatus() != null) {
            todo.setSyncStatus(todoDetails.getSyncStatus());
        }

        return todoRepository.save(todo);
    }

    // ✅ UPDATED: Obriši samo ako pripada korisniku
    public void deleteTodo(Long id, User user) {
        Todo todo = todoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Naloga nije pronađena ili ne pripada vama"));
        todoRepository.delete(todo);
    }

    // ✅ UPDATED: Toggle samo ako pripada korisniku
    public Todo toggleComplete(Long id, User user) {
        Todo todo = todoRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Naloga nije pronađena ili ne pripada vama"));

        todo.setCompleted(!todo.isCompleted());
        return todoRepository.save(todo);
    }

    // ✅ UPDATED: Pretraga samo u Todo-ima korisnika
    public List<Todo> searchByName(String keyword, User user) {
        return todoRepository.findByUserAndNameContainingIgnoreCase(user, keyword);
    }

    // ✅ UPDATED: Filter samo u Todo-ima korisnika
    public List<Todo> filterByCompleted(boolean completed, User user) {
        return todoRepository.findByUserAndCompleted(user, completed);
    }

    // ✅ UPDATED: Statistika samo za korisnika
    public TodoStatisticsDTO getStatistics(User user) {
        long totalTasks = todoRepository.countByUser(user);
        long completedTasks = todoRepository.countByUserAndCompleted(user, true);
        long pendingTasks = todoRepository.countByUserAndCompleted(user, false);

        double completionPercentage = totalTasks > 0
                ? Math.round((completedTasks * 100.0 / totalTasks) * 10.0) / 10.0
                : 0.0;

        Map<String, Long> tasksByCategory = new HashMap<>();
        List<Object[]> categoryResults = todoRepository.countByCategory(user);

        for (Object[] result : categoryResults) {
            Category category = (Category) result[0];
            Long count = (Long) result[1];
            tasksByCategory.put(category.name(), count);
        }

        for (Category category : Category.values()) {
            tasksByCategory.putIfAbsent(category.name(), 0L);
        }

        Map<String, Long> tasksByPriority = new HashMap<>();
        List<Object[]> priorityResults = todoRepository.countByPriority(user);

        for (Object[] result : priorityResults) {
            Priority priority = (Priority) result[0];
            Long count = (Long) result[1];
            tasksByPriority.put(priority.name(), count);
        }

        for (Priority priority : Priority.values()) {
            tasksByPriority.putIfAbsent(priority.name(), 0L);
        }

        LocalDate today = LocalDate.now();
        long overdueTasks = todoRepository.countOverdueTasks(user, today, false);
        long tasksWithoutDueDate = todoRepository.countByUserAndDueDateIsNull(user);

        return new TodoStatisticsDTO(
                totalTasks,
                completedTasks,
                pendingTasks,
                completionPercentage,
                tasksByCategory,
                tasksByPriority,
                overdueTasks,
                tasksWithoutDueDate
        );
    }

    // ✅ UPDATED: Po kategoriji za korisnika
    public List<Todo> getTodosByCategory(Category category, User user) {
        return todoRepository.findByUserAndCategory(user, category);
    }

    // ✅ UPDATED: Po prioritetu za korisnika
    public List<Todo> getTodosByPriority(Priority priority, User user) {
        return todoRepository.findByUserAndPriority(user, priority);
    }

    // ✅ UPDATED: Visoka prioriteta za korisnika
    public List<Todo> getHighPriorityIncompleteTasks(User user) {
        return todoRepository.findHighPriorityIncompleteTasks(user);
    }
}