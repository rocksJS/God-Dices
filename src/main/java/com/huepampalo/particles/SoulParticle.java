package com.huepampalo.particles;

import java.util.UUID;

import net.minecraft.world.phys.Vec3;

public class SoulParticle {

    public Vec3 position;

    public final UUID owner;

    public final float healAmount;

    public SoulParticle(Vec3 position, UUID owner, float healAmount) {

        this.position = position;
        this.owner = owner;
        this.healAmount = healAmount;
    }
}