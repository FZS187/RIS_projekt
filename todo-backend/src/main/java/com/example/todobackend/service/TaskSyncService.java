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

/**
 * ✅ UPDATED: Prava Google Calendar sinhronizacija
 */
@Service
public class TaskSyncService {

    @Autowired
    private TaskSyncStatusRepository syncStatusRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private GoogleCalendarService googleCalendarService;

    @Transactional
    public TaskSyncStatus getOrCreateSyncStatus(Todo todo) {
        return syncStatusRepository.findByTodo(todo)
                .orElseGet(() -> {
                    TaskSyncStatus newStatus = new TaskSyncStatus(todo);
                    return syncStatusRepository.save(newStatus);
                });
    }

    @Transactional
    public TaskSyncStatus startSync(Todo todo) {
        TaskSyncStatus syncStatus = getOrCreateSyncStatus(todo);
        syncStatus.startSync();

        todo.setSyncStatus(SyncStatus.V_TEKU);
        todoRepository.save(todo);

        return syncStatusRepository.save(syncStatus);
    }

    @Transactional
    public TaskSyncStatus completeSync(Todo todo) {
        TaskSyncStatus syncStatus = getOrCreateSyncStatus(todo);
        syncStatus.completeSync();

        todo.setSyncStatus(SyncStatus.USPESNO);
        todoRepository.save(todo);

        return syncStatusRepository.save(syncStatus);
    }

    @Transactional
    public TaskSyncStatus failSync(Todo todo, String errorMessage) {
        TaskSyncStatus syncStatus = getOrCreateSyncStatus(todo);
        syncStatus.failSync(errorMessage);

        todo.setSyncStatus(SyncStatus.NAPAKA);
        todoRepository.save(todo);

        return syncStatusRepository.save(syncStatus);
    }

    public Optional<TaskSyncStatus> getSyncStatus(Long todoId) {
        return syncStatusRepository.findByTodoId(todoId);
    }

    public List<TaskSyncStatus> getTasksInProgress() {
        return syncStatusRepository.findByStatus(TaskSyncStatus.SyncStatus.IN_PROGRESS);
    }

    /**
     * ✅ PRAVA sinhronizacija sa Google Calendar-om
     */
    @Transactional
    public TaskSyncStatus syncWithGoogleCalendar(Todo todo) {
        try {
            startSync(todo);

            String eventId;

            if (todo.getGoogleEventId() == null) {
                // ✅ KREIRAJ novi event
                eventId = googleCalendarService.createCalendarEvent(todo);
                todo.setGoogleEventId(eventId);
                todoRepository.save(todo);

                System.out.println("✅ Kreiran Google Calendar event: " + eventId);
            } else {
                // ✅ AŽURIRAJ postojeći event
                googleCalendarService.updateCalendarEvent(todo.getGoogleEventId(), todo);
                eventId = todo.getGoogleEventId();

                System.out.println("✅ Ažuriran Google Calendar event: " + eventId);
            }

            // Ako je completed, označi event
            if (todo.isCompleted()) {
                googleCalendarService.markEventAsCompleted(eventId);
            }

            return completeSync(todo);

        } catch (Exception e) {
            String errorMsg = "Google Calendar sinhronizacija neuspešna: " + e.getMessage();
            System.err.println("❌ " + errorMsg);
            e.printStackTrace();
            return failSync(todo, errorMsg);
        }
    }

    /**
     * ✅ OBRIŠI Google Calendar event kada se obriše Todo
     */
    @Transactional
    public void deleteGoogleCalendarEvent(Todo todo) {
        try {
            if (todo.getGoogleEventId() != null) {
                googleCalendarService.deleteCalendarEvent(todo.getGoogleEventId());
                System.out.println("✅ Obrisan Google Calendar event: " + todo.getGoogleEventId());
            }
        } catch (Exception e) {
            System.err.println("❌ Greška pri brisanju Google Calendar eventa: " + e.getMessage());
        }
    }

    /**
     * @deprecated Koristi syncWithGoogleCalendar() za pravu sinhronizaciju
     */
    @Deprecated
    @Transactional
    public TaskSyncStatus simulateSync(Todo todo) {
        // Za testiranje bez Google Calendar-a
        return syncWithGoogleCalendar(todo);
    }
}