package com.example.todobackend.repository;

import com.example.todobackend.model.Category;
import com.example.todobackend.model.Priority;
import com.example.todobackend.model.Todo;
import com.example.todobackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * ✅ UPDATED: Dodati metodi za filtriranje po korisniku
 */
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    // ✅ NOVO: Svi Todo-i za određenog korisnika
    List<Todo> findByUser(User user);

    // ✅ NOVO: Pronađi Todo po ID-ju i korisniku (za sigurnost)
    Optional<Todo> findByIdAndUser(Long id, User user);

    // ✅ UPDATED: Filtriranje po completed statusu za određenog korisnika
    List<Todo> findByUserAndCompleted(User user, boolean completed);

    // ✅ UPDATED: Pretraga po imenu za određenog korisnika
    List<Todo> findByUserAndNameContainingIgnoreCase(User user, String name);

    // ✅ UPDATED: Brojanje po completed statusu za korisnika
    long countByUserAndCompleted(User user, boolean completed);

    // ✅ UPDATED: Brojanje preko dueDate za korisnika
    @Query("SELECT COUNT(t) FROM Todo t WHERE t.user = :user AND t.dueDate < :today AND t.completed = :completed")
    long countOverdueTasks(@Param("user") User user, @Param("today") LocalDate today, @Param("completed") boolean completed);

    // ✅ UPDATED: Brojanje bez dueDate za korisnika
    long countByUserAndDueDateIsNull(User user);

    // ✅ UPDATED: Statistika po kategorijama za korisnika
    @Query("SELECT t.category, COUNT(t) FROM Todo t WHERE t.user = :user GROUP BY t.category")
    List<Object[]> countByCategory(@Param("user") User user);

    // ✅ UPDATED: Brojanje po kategoriji za korisnika
    long countByUserAndCategory(User user, Category category);

    // ✅ UPDATED: Naloge određene kategorije za korisnika
    List<Todo> findByUserAndCategory(User user, Category category);

    // ✅ UPDATED: Statistika po prioritetima za korisnika
    @Query("SELECT t.priority, COUNT(t) FROM Todo t WHERE t.user = :user GROUP BY t.priority")
    List<Object[]> countByPriority(@Param("user") User user);

    // ✅ UPDATED: Brojanje po prioritetu za korisnika
    long countByUserAndPriority(User user, Priority priority);

    // ✅ UPDATED: Naloge određene prioritete za korisnika
    List<Todo> findByUserAndPriority(User user, Priority priority);

    // ✅ UPDATED: Sve naloge sortirane po prioritetu i roku za korisnika
    @Query("SELECT t FROM Todo t WHERE t.user = :user ORDER BY t.priority DESC, t.dueDate ASC")
    List<Todo> findAllOrderedByPriorityAndDueDate(@Param("user") User user);

    // ✅ UPDATED: Naloge po kategoriji i completed za korisnika
    List<Todo> findByUserAndCategoryAndCompleted(User user, Category category, boolean completed);

    // ✅ UPDATED: Visoko prioritetne nedovršene naloge za korisnika
    @Query("SELECT t FROM Todo t WHERE t.user = :user AND t.priority = 'HIGH' AND t.completed = false")
    List<Todo> findHighPriorityIncompleteTasks(@Param("user") User user);

    // ✅ NOVO: Brojanje svih Todo-a za korisnika
    long countByUser(User user);
}