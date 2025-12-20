package com.example.todobackend.model;

/**
 * Enum za kategorizacijo nalog
 * Omogoča grupiranje nalog po življenjskih področjih
 *
 * TASK-1: Kreiran za analizo produktivnosti
 */
public enum Category {
    WORK("Delo", "💼"),              // Poslovno
    PERSONAL("Osebno", "🏠"),        // Osebno
    SHOPPING("Nakupovanje", "🛒"),   // Nakupovanje
    HEALTH("Zdravje", "💪"),         // Zdravje
    EDUCATION("Izobraževanje", "📚"), // Izobraževanje
    OTHER("Drugo", "📌");            // Ostalo

    private final String slovenianName;
    private final String icon;

    /**
     * Konstruktor enum vrednosti
     * @param slovenianName Slovenski naziv kategorije
     * @param icon Emoji ikona za vizualni prikaz
     */
    Category(String slovenianName, String icon) {
        this.slovenianName = slovenianName;
        this.icon = icon;
    }

    /**
     * Dobi slovenski naziv kategorije
     * @return Slovenski naziv
     */
    public String getSlovenianName() {
        return slovenianName;
    }

    /**
     * Dobi ikono kategorije
     * @return Emoji ikona
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Najdi kategorijo iz slovenskega imena
     * @param name Slovenski naziv
     * @return Ustrezna kategorija ali OTHER če ni najdena
     */
    public static Category fromSlovenianName(String name) {
        for (Category category : Category.values()) {
            if (category.slovenianName.equalsIgnoreCase(name)) {
                return category;
            }
        }
        return OTHER;
    }

    /**
     * Prikaz za debugging
     */
    @Override
    public String toString() {
        return this.name() + " (" + slovenianName + " " + icon + ")";
    }
}