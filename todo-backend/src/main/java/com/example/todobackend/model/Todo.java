package com.example.todobackend.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entiteta Todo - predstavlja nalogo v aplikaciji
 *
 * TASK-1 SPREMEMBE:
 * - Dodano polje 'description' za podrobnejši opis naloge
 * - Dodano polje 'category' za kategorizacijo (WORK, PERSONAL, SHOPPING, itd.)
 * - Dodano polje 'priority' za določanje pomembnosti (LOW, MEDIUM, HIGH)
 *
 * @author RIS_PROJEKT Tim
 * @version 2.0 - Sprint 1
 */
@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 1000)
    private String description; // NOVO - TASK-1: Podrobnejši opis naloge

    @Column(nullable = false)
    private boolean completed = false;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "reminder_at")
    private LocalDateTime reminderAt;

    // ========== NOVA POLJA - TASK-1 ==========

    /**
     * Kategorija naloge za organizacijo po življenjskih področjih
     * Možne vrednosti: WORK, PERSONAL, SHOPPING, HEALTH, EDUCATION, OTHER
     * Privzeta vrednost: OTHER
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Category category = Category.OTHER;

    /**
     * Prioriteta naloge za določanje nujnosti
     * Možne vrednosti: LOW, MEDIUM, HIGH
     * Privzeta vrednost: MEDIUM
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Priority priority = Priority.MEDIUM;

    // ========== KONSTRUKTORJI ==========

    /**
     * Prazen konstruktor - potreben za JPA
     */
    public Todo() {
    }

    /**
     * Konstruktor z imenom
     * @param name Ime naloge
     */
    public Todo(String name) {
        this.name = name;
    }

    /**
     * Polni konstruktor za kreiranje naloge z vsemi podatki
     * @param name Ime naloge
     * @param description Opis naloge
     * @param category Kategorija naloge
     * @param priority Prioriteta naloge
     */
    public Todo(String name, String description, Category category, Priority priority) {
        this.name = name;
        this.description = description;
        this.category = category != null ? category : Category.OTHER;
        this.priority = priority != null ? priority : Priority.MEDIUM;
    }

    // ========== GETTERI IN SETTERI ==========

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * NOVO - TASK-1
     * @return Podrobnejši opis naloge
     */
    public String getDescription() {
        return description;
    }

    /**
     * NOVO - TASK-1
     * @param description Opis naloge (max 1000 znakov)
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getReminderAt() {
        return reminderAt;
    }

    public void setReminderAt(LocalDateTime reminderAt) {
        this.reminderAt = reminderAt;
    }

    /**
     * NOVO - TASK-1
     * @return Kategorija naloge
     */
    public Category getCategory() {
        return category;
    }

    /**
     * NOVO - TASK-1
     * @param category Kategorija naloge (če je null, nastavi na OTHER)
     */
    public void setCategory(Category category) {
        this.category = category != null ? category : Category.OTHER;
    }

    /**
     * NOVO - TASK-1
     * @return Prioriteta naloge
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * NOVO - TASK-1
     * @param priority Prioriteta naloge (če je null, nastavi na MEDIUM)
     */
    public void setPriority(Priority priority) {
        this.priority = priority != null ? priority : Priority.MEDIUM;
    }

    // ========== POMOŽNE METODE ==========

    /**
     * Preveri, ali je naloga pretečena (due date je v preteklosti)
     * @return true če je rok potekel in naloga ni dokončana
     */
    public boolean isOverdue() {
        if (completed || dueDate == null) {
            return false;
        }
        return dueDate.isBefore(LocalDate.now());
    }

    /**
     * Preveri, ali je naloga visoke prioritete
     * @return true če je prioriteta HIGH
     */
    public boolean isHighPriority() {
        return priority == Priority.HIGH;
    }

    /**
     * Dobi celoten prikaz naloge kot String (za debugging)
     */
    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + (description != null ? description.substring(0, Math.min(description.length(), 50)) + "..." : "N/A") + '\'' +
                ", completed=" + completed +
                ", category=" + category +
                ", priority=" + priority +
                ", dueDate=" + dueDate +
                ", reminderAt=" + reminderAt +
                '}';
    }

    /**
     * Preveri enakost dveh Todo objektov (na podlagi ID-ja)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return id != null && id.equals(todo.id);
    }

    /**
     * Generiraj hash code (na podlagi ID-ja)
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}