package com.huepampalo;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ModServerMessages {

    public static void chatMessageAfterTeleport(ServerPlayer player, int teleportCount) {

        // # LORE

        if (teleportCount < 1) {
            player.sendSystemMessage(Component.literal("Вы телепортировались впервые!"));
        }
        if (teleportCount < 100) {
            player.sendSystemMessage(Component.literal("Вы телепортировались " + teleportCount + " раз!"));
        }
        if (teleportCount >= 100) {
            player.sendSystemMessage(Component.literal(
                    "Сюжет начался! Когда вы уснёте - этого не будет! " + teleportCount + " раз!"));
        }
        if (teleportCount >= 220) {
            player.sendSystemMessage(Component.literal(
                    "Вы телепортировались " + teleportCount + " раз! ! !"));
        }
        if (teleportCount >= 300) {
            player.sendSystemMessage(Component.literal(
                    "Вы телепортировались " + teleportCount + " раз! Трактористы только и думают, что о вас!"));
        }
        if (teleportCount >= 301) {
            player.sendSystemMessage(Component.literal(
                    "Вы телепортировались " + teleportCount + " раз! На каком вы плато? !"));
        }
        if (teleportCount >= 500) {
            player.sendSystemMessage(Component.literal(
                    "Вы телепортировались " + teleportCount + " раз! Вы уже не можете различать где вы... !"));
        }
        if (teleportCount >= 700) {
            player.sendSystemMessage(Component.literal(
                    "Вы телепортировались " + teleportCount + " раз! Вы - бессмертный!"));
        }
        if (teleportCount >= 900) {
            player.sendSystemMessage(Component.literal(
                    "Вы телепортировались " + teleportCount + " раз! Вы - бог!"));
        }

        if (teleportCount >= 1000) {
            player.sendSystemMessage(Component.literal(
                    "Вы телепортировались " + teleportCount + " раз! Вы - бессмертный!"));
        }
        if (teleportCount >= 2000) {
            player.sendSystemMessage(Component.literal(
                    "Вы телепортировались " + teleportCount + " раз! Вы - бог!"));
        }
    }
}
