package com.huepampalo;

public class ModCounters {

    // === Переменная, доступная из всего проекта ===
    public static int teleportCount = 0;

    // === Методы, доступные из всего проекта ===

    /** Увеличивает счётчик телепортаций на 1 */
    public static void addTeleportCounter() {
        teleportCount++;
    }

    /** Возвращает текущее количество телепортаций */
    public static int getTeleportCounter() {
        return teleportCount;
    }

    /** Сбрасывает счётчик в 0 */
    public static void resetTeleportCount() {
        teleportCount = 0;
    }
}