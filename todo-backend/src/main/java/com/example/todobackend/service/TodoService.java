package com.example.todobackend.service;

import com.example.todobackend.dto.TodoStatisticsDTO;
import com.example.todobackend.model.Category;
import com.example.todobackend.model.Priority;
import com.example.todobackend.model.SyncStatus;
import com.example.todobackend.model.Todo;
import com.example.todobackend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    public Todo createTodo(Todo todo) {
        // Nastavi privzete vrednosti
        if (todo.getCategory() == null) {
            todo.setCategory(Category.OTHER);
        }
        if (todo.getPriority() == null) {
            todo.setPriority(Priority.MEDIUM);
        }
        // ✅ ZADRŽAVAMO: Početni syncStatus će biti null
        // TaskSyncService će ga postaviti na V_TEKU kada startuje sync

        return todoRepository.save(todo);
    }

    public Todo updateTodo(Long id, Todo todoDetails) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Naloga z ID " + id + " ni bila najdena"));

        todo.setName(todoDetails.getName());
        todo.setDescription(todoDetails.getDescription());
        todo.setCompleted(todoDetails.isCompleted());
        todo.setDueDate(todoDetails.getDueDate());
        todo.setReminderAt(todoDetails.getReminderAt());
        todo.setCategory(todoDetails.getCategory());
        todo.setPriority(todoDetails.getPriority());

        // ✅ ZADRŽAVAMO: Čuvamo syncStatus ako postoji
        if (todoDetails.getSyncStatus() != null) {
            todo.setSyncStatus(todoDetails.getSyncStatus());
        }

        return todoRepository.save(todo);
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    public Todo toggleComplete(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Naloga z ID " + id + " ni bila najdena"));

        todo.setCompleted(!todo.isCompleted());
        return todoRepository.save(todo);
    }

    public List<Todo> searchByName(String keyword) {
        return todoRepository.findByNameContainingIgnoreCase(keyword);
    }

    public List<Todo> filterByCompleted(boolean completed) {
        return todoRepository.findByCompleted(completed);
    }

    // ========== STATISTIKA ==========

    public TodoStatisticsDTO getStatistics() {
        long totalTasks = todoRepository.count();
        long completedTasks = todoRepository.countByCompleted(true);
        long pendingTasks = todoRepository.countByCompleted(false);

        double completionPercentage = totalTasks > 0
                ? Math.round((completedTasks * 100.0 / totalTasks) * 10.0) / 10.0
                : 0.0;

        Map<String, Long> tasksByCategory = new HashMap<>();
        List<Object[]> categoryResults = todoRepository.countByCategory();

        for (Object[] result : categoryResults) {
            Category category = (Category) result[0];
            Long count = (Long) result[1];
            tasksByCategory.put(category.name(), count);
        }

        for (Category category : Category.values()) {
            tasksByCategory.putIfAbsent(category.name(), 0L);
        }

        Map<String, Long> tasksByPriority = new HashMap<>();
        List<Object[]> priorityResults = todoRepository.countByPriority();

        for (Object[] result : priorityResults) {
            Priority priority = (Priority) result[0];
            Long count = (Long) result[1];
            tasksByPriority.put(priority.name(), count);
        }

        for (Priority priority : Priority.values()) {
            tasksByPriority.putIfAbsent(priority.name(), 0L);
        }

        LocalDate today = LocalDate.now();
        long overdueTasks = todoRepository.countOverdueTasks(today, false);
        long tasksWithoutDueDate = todoRepository.countByDueDateIsNull();

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

    public List<Todo> getTodosByCategory(Category category) {
        return todoRepository.findByCategory(category);
    }

    public List<Todo> getTodosByPriority(Priority priority) {
        return todoRepository.findByPriority(priority);
    }

    public List<Todo> getHighPriorityIncompleteTasks() {
        return todoRepository.findHighPriorityIncompleteTasks();
    }
}