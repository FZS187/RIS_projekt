package com.example.todobackend.model;

/**
 * Enum za prioritet zadataka
 * Določa nujnost opravila
 */
public enum Priority {
    LOW("Nizka", "🟢", 1),        // Niska prioriteta
    MEDIUM("Srednja", "🟡", 2),   // Srednja prioriteta
    HIGH("Visoka", "🔴", 3);      // Visoka prioriteta

    private final String slovenianName;
    private final String icon;
    private final int level; // Za sortiranje

    Priority(String slovenianName, String icon, int level) {
        this.slovenianName = slovenianName;
        this.icon = icon;
        this.level = level;
    }

    public String getSlovenianName() {
        return slovenianName;
    }

    public String getIcon() {
        return icon;
    }

    public int getLevel() {
        return level;
    }

    /**
     * Pridobi prioriteto iz slovenskega imena
     */
    public static Priority fromSlovenianName(String name) {
        for (Priority priority : Priority.values()) {
            if (priority.slovenianName.equalsIgnoreCase(name)) {
                return priority;
            }
        }
        return MEDIUM;
    }
}