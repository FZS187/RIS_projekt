package com.example.todobackend.service;

import com.example.todobackend.model.SyncStatus;
import com.example.todobackend.model.TaskSyncStatus;
import com.example.todobackend.model.Todo;
import com.example.todobackend.repository.TaskSyncStatusRepository;
import com.example.todobackend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskSyncService {

    @Autowired
    private TaskSyncStatusRepository syncStatusRepository;

    @Autowired
    private TodoRepository todoRepository;

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
     * ✅ TASK 1: Postavlja Todo.syncStatus na V_TEKU
     */
    @Transactional
    public TaskSyncStatus startSync(Todo todo) {
        TaskSyncStatus syncStatus = getOrCreateSyncStatus(todo);
        syncStatus.startSync();

        // ✅ Ažuriraj Todo.syncStatus na V_TEKU
        todo.setSyncStatus(SyncStatus.V_TEKU);
        todoRepository.save(todo);

        return syncStatusRepository.save(syncStatus);
    }

    /**
     * Završi sinhronizaciju uspešno - postavi status na COMPLETED
     * ✅ TASK 1: Postavlja Todo.syncStatus na USPESNO
     */
    @Transactional
    public TaskSyncStatus completeSync(Todo todo) {
        TaskSyncStatus syncStatus = getOrCreateSyncStatus(todo);
        syncStatus.completeSync();

        // ✅ Ažuriraj Todo.syncStatus na USPESNO
        todo.setSyncStatus(SyncStatus.USPESNO);
        todoRepository.save(todo);

        return syncStatusRepository.save(syncStatus);
    }

    /**
     * Označi sinhronizaciju kao neuspešnu
     * ✅ TASK 2: Postavlja Todo.syncStatus na NAPAKA u slučaju greške
     */
    @Transactional
    public TaskSyncStatus failSync(Todo todo, String errorMessage) {
        TaskSyncStatus syncStatus = getOrCreateSyncStatus(todo);
        syncStatus.failSync(errorMessage);

        // ✅ Ažuriraj Todo.syncStatus na NAPAKA
        todo.setSyncStatus(SyncStatus.NAPAKA);
        todoRepository.save(todo);

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
     * U realnom scenariju ovde bi bila integracija sa eksternim API-jem (Google Calendar)
     * ✅ TASK 3: Vraća konačni status (uspešno ili napaka)
     */
    @Transactional
    public TaskSyncStatus simulateSync(Todo todo) {
        try {
            // Postavi status na IN_PROGRESS
            startSync(todo);

            // Simuliraj rad (npr. poziv Google Calendar API-ja)
            Thread.sleep(2000); // 2 sekunde

            // Simuliraj nasumičnu grešku (20% šanse)
            if (Math.random() < 0.2) {
                throw new RuntimeException("Google Calendar API connection timeout");
            }

            // Završi uspešno
            return completeSync(todo);

        } catch (Exception e) {
            // ✅ TASK 2: Ako dođe do greške, označi sa statusom NAPAKA
            return failSync(todo, e.getMessage());
        }
    }

    /**
     * ✅ NOVA METODA: Sinhronizuj sa pravim Google Calendar API-jem
     * Ovde bi išla prava implementacija poziva prema Google Calendar-u
     */
    @Transactional
    public TaskSyncStatus syncWithGoogleCalendar(Todo todo) {
        try {
            startSync(todo);

            // TODO: Implementirati pravi Google Calendar API poziv
            // GoogleCalendarService.createEvent(todo);

            // Za sada simuliramo uspeh
            Thread.sleep(1000);

            return completeSync(todo);

        } catch (Exception e) {
            String errorMsg = "Google Calendar sinhronizacija neuspešna: " + e.getMessage();
            return failSync(todo, errorMsg);
        }
    }
}