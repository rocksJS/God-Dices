package com.huepampalo.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record SoulHealthPacket(float value) implements CustomPacketPayload {

    public static void send(
            ServerPlayer player,
            float value) {

        ServerPlayNetworking.send(
                player,
                new SoulHealthPacket(value));
    }

    public static final Type<SoulHealthPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath(
                    "huepampalo",
                    "soul_health"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SoulHealthPacket> CODEC = StreamCodec.of(
            (buf, packet) -> buf.writeFloat(packet.value),
            buf -> new SoulHealthPacket(buf.readFloat()));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}