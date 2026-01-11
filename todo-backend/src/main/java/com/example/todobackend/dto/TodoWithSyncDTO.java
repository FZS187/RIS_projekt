package com.example.todobackend.dto;

import com.example.todobackend.model.SyncStatus;
import com.example.todobackend.model.TaskSyncStatus;
import com.example.todobackend.model.Todo;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ✅ TASK 4 & 5: DTO koji jasno prikazuje status sinhronizacije
 */
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
        private String status; // PENDING, IN_PROGRESS, COMPLETED, FAILED
        private String displayName; // Čakanje, V teku, Zaključeno, Neuspešno
        private String todoSyncStatus; // V_TEKU, USPESNO, NAPAKA (iz Todo modela)
        private LocalDateTime startedAt;
        private LocalDateTime completedAt;
        private String errorMessage;
        private LocalDateTime lastSynced;

        // ✅ TASK 4: Dodatna polja za frontend
        private boolean isSuccess; // Da li je sinhronizacija uspešna
        private boolean isFailed;  // Da li je sinhronizacija neuspešna
        private boolean isInProgress; // Da li je sinhronizacija u toku
        private String userMessage; // ✅ TASK 5: Razumljivo obvestilo za korisnika

        public SyncStatusDTO(TaskSyncStatus syncStatus, Todo todo) {
            if (syncStatus != null) {
                this.status = syncStatus.getStatus().name();
                this.displayName = syncStatus.getStatus().getDisplayName();
                this.startedAt = syncStatus.getStartedAt();
                this.completedAt = syncStatus.getCompletedAt();
                this.errorMessage = syncStatus.getErrorMessage();
                this.lastSynced = syncStatus.getLastSynced();

                // Postavi boolean flagove
                this.isSuccess = syncStatus.getStatus() == TaskSyncStatus.SyncStatus.COMPLETED;
                this.isFailed = syncStatus.getStatus() == TaskSyncStatus.SyncStatus.FAILED;
                this.isInProgress = syncStatus.getStatus() == TaskSyncStatus.SyncStatus.IN_PROGRESS;
            }

            // Uzmi Todo.syncStatus
            if (todo != null && todo.getSyncStatus() != null) {
                this.todoSyncStatus = todo.getSyncStatus().name();
            }

            // ✅ TASK 5: Generiši razumljivo obvestilo za korisnika
            this.userMessage = generateUserMessage();
        }

        /**
         * ✅ TASK 5: Generiši kratko i razumljivo obvestilo za korisnika
         */
        private String generateUserMessage() {
            if (isSuccess) {
                return "✅ Sinhronizacija uspešna";
            } else if (isFailed) {
                if (errorMessage != null && !errorMessage.isEmpty()) {
                    return "❌ Sinhronizacija nije uspela: " + errorMessage;
                }
                return "❌ Sinhronizacija nije uspela. Pokušajte ponovo.";
            } else if (isInProgress) {
                return "⏳ Sinhronizacija u toku...";
            } else {
                return "⏸️ Čeka na sinhronizaciju";
            }
        }

        // Getters and Setters
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getDisplayName() { return displayName; }
        public void setDisplayName(String displayName) { this.displayName = displayName; }

        public String getTodoSyncStatus() { return todoSyncStatus; }
        public void setTodoSyncStatus(String todoSyncStatus) { this.todoSyncStatus = todoSyncStatus; }

        public LocalDateTime getStartedAt() { return startedAt; }
        public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }

        public LocalDateTime getCompletedAt() { return completedAt; }
        public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

        public String getErrorMessage() { return errorMessage; }
        public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

        public LocalDateTime getLastSynced() { return lastSynced; }
        public void setLastSynced(LocalDateTime lastSynced) { this.lastSynced = lastSynced; }

        public boolean isSuccess() { return isSuccess; }
        public void setSuccess(boolean success) { isSuccess = success; }

        public boolean isFailed() { return isFailed; }
        public void setFailed(boolean failed) { isFailed = failed; }

        public boolean isInProgress() { return isInProgress; }
        public void setInProgress(boolean inProgress) { isInProgress = inProgress; }

        public String getUserMessage() { return userMessage; }
        public void setUserMessage(String userMessage) { this.userMessage = userMessage; }
    }

    // Constructor - ✅ TASK 3: Prosledi Todo objekat za pristup syncStatus
    public TodoWithSyncDTO(Todo todo, TaskSyncStatus syncStatus) {
        this.id = todo.getId();
        this.title = todo.getName();
        this.description = todo.getDescription();
        this.completed = todo.isCompleted();
        this.dueDate = todo.getDueDate();
        this.category = todo.getCategory() != null ? todo.getCategory().name() : null;
        this.priority = todo.getPriority() != null ? todo.getPriority().name() : null;
        this.syncStatus = new SyncStatusDTO(syncStatus, todo); // Prosledi i Todo
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