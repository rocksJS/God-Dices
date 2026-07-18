package com.huepampalo.client;

import com.huepampalo.DarkSister;
import com.huepampalo.network.SoulHealthPacket;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.world.entity.player.Player;

public class ClientNetworking {

    public static void register() {

        ClientPlayNetworking.registerGlobalReceiver(
                SoulHealthPacket.TYPE,
                (payload, context) -> {

                    float value = payload.value();

                    context.client().player.sendSystemMessage(
                            net.minecraft.network.chat.Component.literal(
                                    "CLIENT SOUL RECEIVED: " + value));

                    context.client().execute(() -> {

                        ClientSoulData.setSoulHealth(value);

                    });
                });
    }
}