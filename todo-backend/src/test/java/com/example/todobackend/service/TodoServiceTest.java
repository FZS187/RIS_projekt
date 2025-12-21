package com.example.todobackend.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.todobackend.dto.TodoStatisticsDTO;
import com.example.todobackend.model.Category;
import com.example.todobackend.model.Priority;
import com.example.todobackend.repository.TodoRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("TodoService Tests - Filip Sekulovic - Statistika")
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    /**
     * ============================================================
     * TEST 1 - OSNOVNA STATISTIKA
     * ============================================================
     *
     * Namen: Preveri osnovne metrike (total, completed, pending)
     */
    @Test
    @DisplayName("Test za osnovno statistiko (total, completed, pending)")
    void testGetBasicStats() {
        // ===== ARRANGE (Priprava) =====
        when(todoRepository.count()).thenReturn(3L);
        when(todoRepository.countByCompleted(true)).thenReturn(2L);
        when(todoRepository.countByCompleted(false)).thenReturn(1L);
        when(todoRepository.countByCategory()).thenReturn(Collections.emptyList());
        when(todoRepository.countByPriority()).thenReturn(Collections.emptyList());
        when(todoRepository.countOverdueTasks(any(LocalDate.class), eq(false))).thenReturn(1L);
        when(todoRepository.countByDueDateIsNull()).thenReturn(0L);

        // ===== ACT (Izvršitev) =====
        TodoStatisticsDTO stats = todoService.getStatistics();

        // ===== ASSERT (Preverjanje) =====
        assertNotNull(stats);
        assertEquals(3L, stats.getTotalTasks());
        assertEquals(2L, stats.getCompletedTasks());
        assertEquals(1L, stats.getPendingTasks());
        assertEquals(66.7, stats.getCompletionPercentage(), 0.001);
        assertEquals(1L, stats.getOverdueTasks());
        assertEquals(0L, stats.getTasksWithoutDueDate());

        verify(todoRepository, times(1)).count();
        verify(todoRepository, times(1)).countByCompleted(true);
        verify(todoRepository, times(1)).countByCompleted(false);

        System.out.println("Filip Sekulovic - TODO TEST 1 OK: Osnovna statistika pravilno izracunana");
    }

    /**
     * ============================================================
     * TEST 2 - STATISTIKA PO KATEGORIJAH
     * ============================================================
     *
     * Namen: Preveri pravilno grupiranje po kategorijah
     */
    @Test
    @DisplayName("Test za statistiko po kategorijah")
    void testGetStatsByCategory() {
        // ===== ARRANGE (Priprava) =====
        List<Object[]> categoryResults = Arrays.asList(
                new Object[] { Category.WORK, 2L },
                new Object[] { Category.PERSONAL, 1L });

        when(todoRepository.count()).thenReturn(3L);
        when(todoRepository.countByCompleted(true)).thenReturn(1L);
        when(todoRepository.countByCompleted(false)).thenReturn(2L);
        when(todoRepository.countByCategory()).thenReturn(categoryResults);
        when(todoRepository.countByPriority()).thenReturn(Collections.emptyList());
        when(todoRepository.countOverdueTasks(any(LocalDate.class), eq(false))).thenReturn(0L);
        when(todoRepository.countByDueDateIsNull()).thenReturn(0L);

        // ===== ACT (Izvršitev) =====
        TodoStatisticsDTO stats = todoService.getStatistics();
        Map<String, Long> tasksByCategory = stats.getTasksByCategory();

        // ===== ASSERT (Preverjanje) =====
        assertNotNull(tasksByCategory);
        assertEquals(2L, tasksByCategory.get(Category.WORK.name()));
        assertEquals(1L, tasksByCategory.get(Category.PERSONAL.name()));
        assertEquals(0L, tasksByCategory.get(Category.OTHER.name()));

        System.out.println("Filip Sekulovic - TODO TEST 2 OK: Statistika po kategorijah pravilna");
    }

    /**
     * ============================================================
     * TEST 3 - STATISTIKA PO PRIORITETI
     * ============================================================
     *
     * Namen: Preveri pravilno grupiranje po prioriteti
     */
    @Test
    @DisplayName("Test za statistiko po prioriteti")
    void testGetStatsByPriority() {
        // ===== ARRANGE (Priprava) =====
        List<Object[]> priorityResults = Arrays.asList(
                new Object[] { Priority.HIGH, 1L },
                new Object[] { Priority.MEDIUM, 1L },
                new Object[] { Priority.LOW, 1L });

        when(todoRepository.count()).thenReturn(3L);
        when(todoRepository.countByCompleted(true)).thenReturn(2L);
        when(todoRepository.countByCompleted(false)).thenReturn(1L);
        when(todoRepository.countByCategory()).thenReturn(Collections.emptyList());
        when(todoRepository.countByPriority()).thenReturn(priorityResults);
        when(todoRepository.countOverdueTasks(any(LocalDate.class), eq(false))).thenReturn(0L);
        when(todoRepository.countByDueDateIsNull()).thenReturn(0L);

        // ===== ACT (Izvršitev) =====
        TodoStatisticsDTO stats = todoService.getStatistics();
        Map<String, Long> tasksByPriority = stats.getTasksByPriority();

        // ===== ASSERT (Preverjanje) =====
        assertNotNull(tasksByPriority);
        assertEquals(1L, tasksByPriority.get(Priority.HIGH.name()));
        assertEquals(1L, tasksByPriority.get(Priority.MEDIUM.name()));
        assertEquals(1L, tasksByPriority.get(Priority.LOW.name()));

        System.out.println("Filip Sekulovic - TODO TEST 3 OK: Statistika po prioriteti pravilna");
    }

    /**
     * ============================================================
     * TEST 4 - PRAZNA BAZA PODATKOV
     * ============================================================
     *
     * Namen: Preveri, da so vsi rezultati 0 pri prazni bazi
     */
    @Test
    @DisplayName("Test za prazno bazo podatkov")
    void testEmptyDatabase() {
        // ===== ARRANGE (Priprava) =====
        when(todoRepository.count()).thenReturn(0L);
        when(todoRepository.countByCompleted(true)).thenReturn(0L);
        when(todoRepository.countByCompleted(false)).thenReturn(0L);
        when(todoRepository.countByCategory()).thenReturn(Collections.emptyList());
        when(todoRepository.countByPriority()).thenReturn(Collections.emptyList());
        when(todoRepository.countOverdueTasks(any(LocalDate.class), eq(false))).thenReturn(0L);
        when(todoRepository.countByDueDateIsNull()).thenReturn(0L);

        // ===== ACT (Izvršitev) =====
        TodoStatisticsDTO stats = todoService.getStatistics();

        // ===== ASSERT (Preverjanje) =====
        assertEquals(0L, stats.getTotalTasks());
        assertEquals(0L, stats.getCompletedTasks());
        assertEquals(0L, stats.getPendingTasks());
        assertEquals(0.0, stats.getCompletionPercentage(), 0.001);

        for (Category category : Category.values()) {
            assertEquals(0L, stats.getTasksByCategory().get(category.name()));
        }
        for (Priority priority : Priority.values()) {
            assertEquals(0L, stats.getTasksByPriority().get(priority.name()));
        }

        verify(todoRepository, times(1)).count();
        verify(todoRepository, times(1)).countByCompleted(true);
        verify(todoRepository, times(1)).countByCompleted(false);

        System.out.println("Filip Sekulovic - TODO TEST 4 OK: Prazna baza vrne vse 0");
    }
}
