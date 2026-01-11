package com.example.todobackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "task_sync_status")
public class TaskSyncStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "todo_id", unique = true)
    private Todo todo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SyncStatus status = SyncStatus.PENDING;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "error_message", length = 500)
    private String errorMessage;

    @Column(name = "last_synced")
    private LocalDateTime lastSynced;

    // Enum za statuse
    public enum SyncStatus {
        PENDING("Čakanje"),           // Čeka na sinhronizaciju
        IN_PROGRESS("V teku"),        // Sinhronizacija u toku
        COMPLETED("Zaključeno"),      // Završeno uspešno
        FAILED("Neuspešno");          // Neuspešno

        private final String displayName;

        SyncStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Constructors
    public TaskSyncStatus() {
        this.lastSynced = LocalDateTime.now();
    }

    public TaskSyncStatus(Todo todo) {
        this.todo = todo;
        this.status = SyncStatus.PENDING;
        this.lastSynced = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Todo getTodo() {
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    public SyncStatus getStatus() {
        return status;
    }

    public void setStatus(SyncStatus status) {
        this.status = status;
        this.lastSynced = LocalDateTime.now();
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getLastSynced() {
        return lastSynced;
    }

    public void setLastSynced(LocalDateTime lastSynced) {
        this.lastSynced = lastSynced;
    }

    // Helper metode
    public void startSync() {
        this.status = SyncStatus.IN_PROGRESS;
        this.startedAt = LocalDateTime.now();
        this.lastSynced = LocalDateTime.now();
    }

    public void completeSync() {
        this.status = SyncStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
        this.lastSynced = LocalDateTime.now();
        this.errorMessage = null;
    }

    public void failSync(String errorMessage) {
        this.status = SyncStatus.FAILED;
        this.completedAt = LocalDateTime.now();
        this.lastSynced = LocalDateTime.now();
        this.errorMessage = errorMessage;
    }
}