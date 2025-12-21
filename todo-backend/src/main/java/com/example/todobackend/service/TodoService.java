package com.example.todobackend.service;

import com.example.todobackend.dto.TodoStatisticsDTO;
import com.example.todobackend.model.Category;
import com.example.todobackend.model.Priority;
import com.example.todobackend.model.Todo;
import com.example.todobackend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Storitev za upravljanje nalog
 * Vsebuje poslovno logiko aplikacije
 *
 * TASK-2: Dodana metoda getStatistics() za analizo produktivnosti
 */
@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    // ========== OBSTOJECE METODE (Äe Å¾e obstajajo) ==========

    /**
     * Dobi vse naloge
     */
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    /**
     * Dobi nalogo po ID-ju
     */
    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    /**
     * Ustvari novo nalogo
     */
    public Todo createTodo(Todo todo) {
        // Nastavi privzete vrednosti, Äe niso podane
        if (todo.getCategory() == null) {
            todo.setCategory(Category.OTHER);
        }
        if (todo.getPriority() == null) {
            todo.setPriority(Priority.MEDIUM);
        }
        return todoRepository.save(todo);
    }

    /**
     * Posodobi obstojeÄo nalogo
     */
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

        return todoRepository.save(todo);
    }

    /**
     * IzbriÅ¡i nalogo
     */
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    /**
     * Preklopi status dokonÄanosti
     */
    public Todo toggleComplete(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Naloga z ID " + id + " ni bila najdena"));

        todo.setCompleted(!todo.isCompleted());
        return todoRepository.save(todo);
    }

    /**
     * Najdi naloge po imenu
     */
    public List<Todo> searchByName(String keyword) {
        return todoRepository.findByNameContainingIgnoreCase(keyword);
    }

    /**
     * Filtriraj naloge po statusu
     */
    public List<Todo> filterByCompleted(boolean completed) {
        return todoRepository.findByCompleted(completed);
    }

    // ========== NOVA METODA ZA TASK-2 - STATISTIKA ==========

    /**
     * TASK-2: Dobi celotno statistiko nalog
     *
     * Pridobi analizo produktivnosti uporabnika:
     * - Skupno Å¡tevilo nalog
     * - Å tevilo dokonÄanih/nedokonÄanih nalog
     * - Odstotek dokonÄanosti
     * - Razdelitev po kategorijah
     * - Razdelitev po prioritetah
     * - Å tevilo preteÄenih nalog
     *
     * @return TodoStatisticsDTO z vso statistiko
     */
    public TodoStatisticsDTO getStatistics() {
        // 1. OSNOVNA STATISTIKA
        long totalTasks = todoRepository.count();
        long completedTasks = todoRepository.countByCompleted(true);
        long pendingTasks = todoRepository.countByCompleted(false);

        // IzraÄun odstotka dokonÄanosti (prepreÄi deljenje z 0)
        double completionPercentage = totalTasks > 0
                ? Math.round((completedTasks * 100.0 / totalTasks) * 10.0) / 10.0  // ZaokroÅ¾i na 1 decimalno mesto
                : 0.0;

        // 2. STATISTIKA PO KATEGORIJAH
        Map<String, Long> tasksByCategory = new HashMap<>();
        List<Object[]> categoryResults = todoRepository.countByCategory();

        for (Object[] result : categoryResults) {
            Category category = (Category) result[0];
            Long count = (Long) result[1];
            tasksByCategory.put(category.name(), count);
        }

        // Dodaj vse kategorije z 0, Äe ne obstajajo v bazi
        for (Category category : Category.values()) {
            tasksByCategory.putIfAbsent(category.name(), 0L);
        }

        // 3. STATISTIKA PO PRIORITETAH
        Map<String, Long> tasksByPriority = new HashMap<>();
        List<Object[]> priorityResults = todoRepository.countByPriority();

        for (Object[] result : priorityResults) {
            Priority priority = (Priority) result[0];
            Long count = (Long) result[1];
            tasksByPriority.put(priority.name(), count);
        }

        // Dodaj vse prioritete z 0, Äe ne obstajajo v bazi
        for (Priority priority : Priority.values()) {
            tasksByPriority.putIfAbsent(priority.name(), 0L);
        }

        // 4. DODATNA STATISTIKA
        LocalDate today = LocalDate.now();
        long overdueTasks = todoRepository.countOverdueTasks(today, false); // Samo nedokonÄane preteÄene
        long tasksWithoutDueDate = todoRepository.countByDueDateIsNull();

        // 5. SESTAVI DTO IN VRNI
        TodoStatisticsDTO statistics = new TodoStatisticsDTO(
                totalTasks,
                completedTasks,
                pendingTasks,
                completionPercentage,
                tasksByCategory,
                tasksByPriority,
                overdueTasks,
                tasksWithoutDueDate
        );

        return statistics;
    }

    /**
     * Dodatna metoda: Dobi naloge po kategoriji
     */
    public List<Todo> getTodosByCategory(Category category) {
        return todoRepository.findByCategory(category);
    }

    /**
     * Dodatna metoda: Dobi naloge po prioriteti
     */
    public List<Todo> getTodosByPriority(Priority priority) {
        return todoRepository.findByPriority(priority);
    }

    /**
     * Dodatna metoda: Dobi vse visoko prioritetne nedokonÄane naloge
     */
    public List<Todo> getHighPriorityIncompleteTasks() {
        return todoRepository.findHighPriorityIncompleteTasks();
    }
}
