package com.huepampalo;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

import java.util.List;
import java.util.Random;

public class ModRandoms {

    // Список возможных эффектов (доступен из любого класса)
    public static final List<MobEffect> POSSIBLE_EFFECTS = List.of(
            MobEffects.MOVEMENT_SPEED.value(), // Скорость
            MobEffects.DAMAGE_BOOST.value(), // Сила
            MobEffects.REGENERATION.value(), // Регенерация
            MobEffects.DAMAGE_RESISTANCE.value(), // Сопротивление урону
            MobEffects.ABSORPTION.value() // Поглощение
    );

    // Один общий Random (лучше, чем создавать новый каждый раз)
    private static final Random RANDOM = new Random();

    /**
     * Возвращает случайный эффект из списка
     */
    public static MobEffect getRandomEffect() {
        return POSSIBLE_EFFECTS.get(RANDOM.nextInt(POSSIBLE_EFFECTS.size()));
    }
}