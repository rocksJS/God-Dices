package com.huepampalo.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;

public class ModPackets {

    public static void register() {

        PayloadTypeRegistry.playS2C()
                .register(
                        SoulHealthPacket.TYPE,
                        SoulHealthPacket.CODEC);
    }
}