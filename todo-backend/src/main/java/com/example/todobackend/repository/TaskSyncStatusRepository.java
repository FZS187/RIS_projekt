package com.example.todobackend.repository;

import com.example.todobackend.model.TaskSyncStatus;
import com.example.todobackend.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface TaskSyncStatusRepository extends JpaRepository<TaskSyncStatus, Long> {

    // Pronađi sync status za određenu todo nalog
    Optional<TaskSyncStatus> findByTodo(Todo todo);

    // Pronađi sync status po ID-u todo naloge
    Optional<TaskSyncStatus> findByTodoId(Long todoId);

    // Pronađi sve naloge sa određenim statusom
    List<TaskSyncStatus> findByStatus(TaskSyncStatus.SyncStatus status);

    // Proveri da li todo ima sync status
    boolean existsByTodo(Todo todo);
}