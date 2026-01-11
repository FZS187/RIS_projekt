package com.example.todobackend.model;

/**
 * ✅ Enum za Todo.syncStatus polje
 * Sinhronizovan sa TaskSyncStatus.SyncStatus
 */
public enum SyncStatus {
    V_TEKU("V teku"),        // Mapira se na TaskSyncStatus.IN_PROGRESS
    USPESNO("Uspešno"),      // Mapira se na TaskSyncStatus.COMPLETED
    NAPAKA("Neuspešno");     // Mapira se na TaskSyncStatus.FAILED

    private final String displayName;

    SyncStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * ✅ Mapiranje iz TaskSyncStatus.SyncStatus u SyncStatus
     */
    public static SyncStatus fromTaskSyncStatus(TaskSyncStatus.SyncStatus taskStatus) {
        if (taskStatus == null) {
            return null;
        }

        switch (taskStatus) {
            case IN_PROGRESS:
                return V_TEKU;
            case COMPLETED:
                return USPESNO;
            case FAILED:
                return NAPAKA;
            case PENDING:
            default:
                return null; // PENDING nema ekvivalent u Todo.syncStatus
        }
    }

    /**
     * ✅ Mapiranje iz SyncStatus u TaskSyncStatus.SyncStatus
     */
    public TaskSyncStatus.SyncStatus toTaskSyncStatus() {
        switch (this) {
            case V_TEKU:
                return TaskSyncStatus.SyncStatus.IN_PROGRESS;
            case USPESNO:
                return TaskSyncStatus.SyncStatus.COMPLETED;
            case NAPAKA:
                return TaskSyncStatus.SyncStatus.FAILED;
            default:
                return TaskSyncStatus.SyncStatus.PENDING;
        }
    }
}