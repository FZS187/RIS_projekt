package com.example.todobackend.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ✅ FIXED: Dodata veza sa User entitetom
 * Sada svaki Todo pripada određenom korisniku
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
    private String description;

    @Column(nullable = false)
    private boolean completed = false;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "reminder_at")
    private LocalDateTime reminderAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Category category = Category.OTHER;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Priority priority = Priority.MEDIUM;

    @Enumerated(EnumType.STRING)
    @Column(name = "sync_status", length = 20)
    private SyncStatus syncStatus;

    // ✅ NOVO: Veza sa korisnikom
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore // Ne šalji user podatke u JSON responsu
    private User user;

    // ✅ NOVO: Google Calendar Event ID (za sinhronizaciju)
    @Column(name = "google_event_id", length = 255)
    private String googleEventId;

    // Konstruktori
    public Todo() {
    }

    public Todo(String name) {
        this.name = name;
    }

    public Todo(String name, String description, Category category, Priority priority) {
        this.name = name;
        this.description = description;
        this.category = category != null ? category : Category.OTHER;
        this.priority = priority != null ? priority : Priority.MEDIUM;
    }

    // ✅ NOVI konstruktor sa userom
    public Todo(String name, User user) {
        this.name = name;
        this.user = user;
    }

    // Getteri i Setteri
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

    public String getDescription() {
        return description;
    }

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category != null ? category : Category.OTHER;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority != null ? priority : Priority.MEDIUM;
    }

    public SyncStatus getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(SyncStatus syncStatus) {
        this.syncStatus = syncStatus;
    }

    // ✅ NOVI getteri/setteri
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGoogleEventId() {
        return googleEventId;
    }

    public void setGoogleEventId(String googleEventId) {
        this.googleEventId = googleEventId;
    }

    // Pomoćne metode
    public boolean isOverdue() {
        if (completed || dueDate == null) {
            return false;
        }
        return dueDate.isBefore(LocalDate.now());
    }

    public boolean isHighPriority() {
        return priority == Priority.HIGH;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user=" + (user != null ? user.getEmail() : "null") +
                ", completed=" + completed +
                ", category=" + category +
                ", priority=" + priority +
                ", syncStatus=" + syncStatus +
                ", googleEventId='" + googleEventId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return id != null && id.equals(todo.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}