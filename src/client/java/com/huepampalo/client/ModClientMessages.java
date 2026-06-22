package com.huepampalo.client;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ModClientMessages {

    public static void chatMessageAfterTeleport(ServerPlayer player, int teleportCount) {

        if (teleportCount < 10) {
            player.sendSystemMessage(Component.literal("Вы телепортировались впервые!"));
        } else if (teleportCount > 100) {
            player.sendSystemMessage(Component.literal("Вы телепортировались " + teleportCount + " раз!"));
        }
        player.sendSystemMessage(Component.literal("Телепортации: " + teleportCount));
    }
}
