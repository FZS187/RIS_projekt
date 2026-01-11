package com.example.todobackend.dto;

import com.example.todobackend.model.TaskSyncStatus;
import com.example.todobackend.model.Todo;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TodoWithSyncDTO {

    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDate dueDate;
    private String category;
    private String priority;
    private SyncStatusDTO syncStatus;

    // Nested DTO za sync status
    public static class SyncStatusDTO {
        private String status;
        private String displayName;
        private LocalDateTime startedAt;
        private LocalDateTime completedAt;
        private String errorMessage;
        private LocalDateTime lastSynced;

        public SyncStatusDTO(TaskSyncStatus syncStatus) {
            if (syncStatus != null) {
                this.status = syncStatus.getStatus().name();
                this.displayName = syncStatus.getStatus().getDisplayName();
                this.startedAt = syncStatus.getStartedAt();
                this.completedAt = syncStatus.getCompletedAt();
                this.errorMessage = syncStatus.getErrorMessage();
                this.lastSynced = syncStatus.getLastSynced();
            }
        }

        // Getters and Setters
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getDisplayName() { return displayName; }
        public void setDisplayName(String displayName) { this.displayName = displayName; }

        public LocalDateTime getStartedAt() { return startedAt; }
        public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }

        public LocalDateTime getCompletedAt() { return completedAt; }
        public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

        public String getErrorMessage() { return errorMessage; }
        public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

        public LocalDateTime getLastSynced() { return lastSynced; }
        public void setLastSynced(LocalDateTime lastSynced) { this.lastSynced = lastSynced; }
    }

    // Constructor
    public TodoWithSyncDTO(Todo todo, TaskSyncStatus syncStatus) {
        this.id = todo.getId();
        this.title = todo.getName();
        this.description = todo.getDescription();
        this.completed = todo.isCompleted();
        this.dueDate = todo.getDueDate();
        this.category = todo.getCategory() != null ? todo.getCategory().name() : null;
        this.priority = todo.getPriority() != null ? todo.getPriority().name() : null;
        this.syncStatus = new SyncStatusDTO(syncStatus);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public SyncStatusDTO getSyncStatus() { return syncStatus; }
    public void setSyncStatus(SyncStatusDTO syncStatus) { this.syncStatus = syncStatus; }
}