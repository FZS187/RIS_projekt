package com.example.todobackend.repository;

import com.example.todobackend.model.Category;
import com.example.todobackend.model.Priority;
import com.example.todobackend.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository za Todo entiteto
 * Vsebuje metode za delo z bazo podatkov
 *
 * TASK-2: Dodane metode za statistiko nalog
 */
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    // ========== OBSTOJECE METODE (če že obstajajo) ==========

    /**
     * Najdi vse naloge glede na status dokončanosti
     */
    List<Todo> findByCompleted(boolean completed);

    /**
     * Najdi vse naloge po imenu (case insensitive)
     */
    List<Todo> findByNameContainingIgnoreCase(String name);

    // ========== NOVE METODE ZA TASK-2 - OSNOVNA STATISTIKA ==========

    /**
     * Preštej vse naloge glede na status dokončanosti
     * @param completed true za dokončane, false za nedokončane
     * @return Število nalog
     */
    long countByCompleted(boolean completed);

    /**
     * Preštej vse naloge z iztečenim rokom
     * @param today Današnji datum
     * @param completed Status dokončanosti
     * @return Število pretečenih nalog
     */
    @Query("SELECT COUNT(t) FROM Todo t WHERE t.dueDate < :today AND t.completed = :completed")
    long countOverdueTasks(LocalDate today, boolean completed);

    /**
     * Preštej vse naloge brez določenega roka
     * @return Število nalog brez roka
     */
    long countByDueDateIsNull();

    // ========== NOVE METODE ZA TASK-2 - STATISTIKA PO KATEGORIJAH ==========

    /**
     * Dobi statistiko po kategorijah
     * Vrne pare: [Category, COUNT]
     * Primer rezultata: [[WORK, 15], [PERSONAL, 8], [SHOPPING, 5]]
     */
    @Query("SELECT t.category, COUNT(t) FROM Todo t GROUP BY t.category")
    List<Object[]> countByCategory();

    /**
     * Preštej naloge za določeno kategorijo
     * @param category Kategorija
     * @return Število nalog v tej kategoriji
     */
    long countByCategory(Category category);

    /**
     * Najdi vse naloge določene kategorije
     * @param category Kategorija
     * @return Seznam nalog
     */
    List<Todo> findByCategory(Category category);

    // ========== NOVE METODE ZA TASK-2 - STATISTIKA PO PRIORITETAH ==========

    /**
     * Dobi statistiko po prioritetah
     * Vrne pare: [Priority, COUNT]
     * Primer rezultata: [[HIGH, 10], [MEDIUM, 20], [LOW, 5]]
     */
    @Query("SELECT t.priority, COUNT(t) FROM Todo t GROUP BY t.priority")
    List<Object[]> countByPriority();

    /**
     * Preštej naloge za določeno prioriteto
     * @param priority Prioriteta
     * @return Število nalog te prioritete
     */
    long countByPriority(Priority priority);

    /**
     * Najdi vse naloge določene prioritete
     * @param priority Prioriteta
     * @return Seznam nalog
     */
    List<Todo> findByPriority(Priority priority);

    // ========== DODATNE POMOŽNE METODE ==========

    /**
     * Najdi vse naloge, urejene po prioriteti (HIGH -> MEDIUM -> LOW)
     */
    @Query("SELECT t FROM Todo t ORDER BY t.priority DESC, t.dueDate ASC")
    List<Todo> findAllOrderedByPriorityAndDueDate();

    /**
     * Najdi vse dokončane naloge določene kategorije
     */
    List<Todo> findByCategoryAndCompleted(Category category, boolean completed);

    /**
     * Najdi vse nedokončane naloge visoke prioritete
     */
    @Query("SELECT t FROM Todo t WHERE t.priority = 'HIGH' AND t.completed = false")
    List<Todo> findHighPriorityIncompleteTasks();
}