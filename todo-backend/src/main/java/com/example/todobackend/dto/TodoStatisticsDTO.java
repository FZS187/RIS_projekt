package com.example.todobackend.dto;

import java.util.Map;

/**
 * DTO za statistiko nalog
 * Uporablja se za prikaz analize produktivnosti uporabnika
 *
 * TASK-2: Kreiran za analizo dokončanih nalog, kategorij in prioritet
 *
 * @author RIS_PROJEKT Tim
 * @version 1.0 - Sprint 1
 */
public class TodoStatisticsDTO {

    // ========== OSNOVNA STATISTIKA ==========

    /**
     * Skupno število nalog v sistemu
     */
    private long totalTasks;

    /**
     * Število dokončanih nalog
     */
    private long completedTasks;

    /**
     * Število nedokončanih nalog (pending)
     */
    private long pendingTasks;

    /**
     * Odstotek dokončanih nalog (0-100)
     */
    private double completionPercentage;

    // ========== STATISTIKA PO KATEGORIJAH ==========

    /**
     * Število nalog po kategorijah
     * Primer: {"WORK": 15, "PERSONAL": 8, "SHOPPING": 5}
     */
    private Map<String, Long> tasksByCategory;

    // ========== STATISTIKA PO PRIORITETAH ==========

    /**
     * Število nalog po prioritetah
     * Primer: {"HIGH": 10, "MEDIUM": 20, "LOW": 5}
     */
    private Map<String, Long> tasksByPriority;

    // ========== DODATNA STATISTIKA ==========

    /**
     * Število nalog z iztečenim rokom (overdue)
     */
    private long overdueTasks;

    /**
     * Število nalog brez določenega roka
     */
    private long tasksWithoutDueDate;

    // ========== KONSTRUKTORJI ==========

    /**
     * Prazen konstruktor - potreben za Jackson JSON deserializacijo
     */
    public TodoStatisticsDTO() {
    }

    /**
     * Osnoven konstruktor z osnovno statistiko
     */
    public TodoStatisticsDTO(long totalTasks, long completedTasks, long pendingTasks,
                             double completionPercentage) {
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.pendingTasks = pendingTasks;
        this.completionPercentage = completionPercentage;
    }

    /**
     * Polni konstruktor z vso statistiko
     */
    public TodoStatisticsDTO(long totalTasks, long completedTasks, long pendingTasks,
                             double completionPercentage, Map<String, Long> tasksByCategory,
                             Map<String, Long> tasksByPriority, long overdueTasks,
                             long tasksWithoutDueDate) {
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.pendingTasks = pendingTasks;
        this.completionPercentage = completionPercentage;
        this.tasksByCategory = tasksByCategory;
        this.tasksByPriority = tasksByPriority;
        this.overdueTasks = overdueTasks;
        this.tasksWithoutDueDate = tasksWithoutDueDate;
    }

    // ========== GETTERI IN SETTERI ==========

    public long getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(long totalTasks) {
        this.totalTasks = totalTasks;
    }

    public long getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(long completedTasks) {
        this.completedTasks = completedTasks;
    }

    public long getPendingTasks() {
        return pendingTasks;
    }

    public void setPendingTasks(long pendingTasks) {
        this.pendingTasks = pendingTasks;
    }

    public double getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(double completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public Map<String, Long> getTasksByCategory() {
        return tasksByCategory;
    }

    public void setTasksByCategory(Map<String, Long> tasksByCategory) {
        this.tasksByCategory = tasksByCategory;
    }

    public Map<String, Long> getTasksByPriority() {
        return tasksByPriority;
    }

    public void setTasksByPriority(Map<String, Long> tasksByPriority) {
        this.tasksByPriority = tasksByPriority;
    }

    public long getOverdueTasks() {
        return overdueTasks;
    }

    public void setOverdueTasks(long overdueTasks) {
        this.overdueTasks = overdueTasks;
    }

    public long getTasksWithoutDueDate() {
        return tasksWithoutDueDate;
    }

    public void setTasksWithoutDueDate(long tasksWithoutDueDate) {
        this.tasksWithoutDueDate = tasksWithoutDueDate;
    }

    // ========== POMOŽNE METODE ==========

    /**
     * Preveri, ali obstajajo naloge v sistemu
     */
    public boolean hasTasks() {
        return totalTasks > 0;
    }

    /**
     * Preveri, ali je stopnja dokončanosti visoka (> 75%)
     */
    public boolean hasHighCompletionRate() {
        return completionPercentage > 75.0;
    }

    /**
     * Preveri, ali je stopnja dokončanosti nizka (< 25%)
     */
    public boolean hasLowCompletionRate() {
        return completionPercentage < 25.0;
    }

    /**
     * Dobi število nalog za določeno kategorijo
     */
    public long getTasksForCategory(String category) {
        return tasksByCategory != null ? tasksByCategory.getOrDefault(category, 0L) : 0L;
    }

    /**
     * Dobi število nalog za določeno prioriteto
     */
    public long getTasksForPriority(String priority) {
        return tasksByPriority != null ? tasksByPriority.getOrDefault(priority, 0L) : 0L;
    }

    @Override
    public String toString() {
        return "TodoStatisticsDTO{" +
                "totalTasks=" + totalTasks +
                ", completedTasks=" + completedTasks +
                ", pendingTasks=" + pendingTasks +
                ", completionPercentage=" + String.format("%.1f", completionPercentage) + "%" +
                ", overdueTasks=" + overdueTasks +
                ", tasksWithoutDueDate=" + tasksWithoutDueDate +
                '}';
    }
}