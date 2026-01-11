package com.example.todobackend.service;

import com.example.todobackend.model.TaskSyncStatus;
import com.example.todobackend.model.Todo;
import com.example.todobackend.repository.TaskSyncStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskSyncService {

    @Autowired
    private TaskSyncStatusRepository syncStatusRepository;

    /**
     * Kreiraj ili dohvati sync status za todo nalog
     */
    @Transactional
    public TaskSyncStatus getOrCreateSyncStatus(Todo todo) {
        return syncStatusRepository.findByTodo(todo)
                .orElseGet(() -> {
                    TaskSyncStatus newStatus = new TaskSyncStatus(todo);
                    return syncStatusRepository.save(newStatus);
                });
    }

    /**
     * Pokreni sinhronizaciju - postavi status na IN_PROGRESS
     */
    @Transactional
    public TaskSyncStatus startSync(Todo todo) {
        TaskSyncStatus syncStatus = getOrCreateSyncStatus(todo);
        syncStatus.startSync();
        return syncStatusRepository.save(syncStatus);
    }

    /**
     * Završi sinhronizaciju uspešno - postavi status na COMPLETED
     */
    @Transactional
    public TaskSyncStatus completeSync(Todo todo) {
        TaskSyncStatus syncStatus = getOrCreateSyncStatus(todo);
        syncStatus.completeSync();
        return syncStatusRepository.save(syncStatus);
    }

    /**
     * Označi sinhronizaciju kao neuspešnu
     */
    @Transactional
    public TaskSyncStatus failSync(Todo todo, String errorMessage) {
        TaskSyncStatus syncStatus = getOrCreateSyncStatus(todo);
        syncStatus.failSync(errorMessage);
        return syncStatusRepository.save(syncStatus);
    }

    /**
     * Dohvati sync status za određenu nalog
     */
    public Optional<TaskSyncStatus> getSyncStatus(Long todoId) {
        return syncStatusRepository.findByTodoId(todoId);
    }

    /**
     * Dohvati sve naloge koje se trenutno sinhronizuju
     */
    public List<TaskSyncStatus> getTasksInProgress() {
        return syncStatusRepository.findByStatus(TaskSyncStatus.SyncStatus.IN_PROGRESS);
    }

    /**
     * Simulacija sinhronizacije (za testiranje)
     * U realnom scenariju ovde bi bila integracija sa eksternim API-jem
     */
    @Transactional
    public void simulateSync(Todo todo) {
        try {
            // Postavi status na IN_PROGRESS
            startSync(todo);

            // Simuliraj rad (npr. poziv eksternog API-ja)
            Thread.sleep(2000); // 2 sekunde

            // Završi uspešno
            completeSync(todo);

        } catch (Exception e) {
            // Ako dođe do greške
            failSync(todo, e.getMessage());
        }
    }
}