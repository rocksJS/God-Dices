package com.huepampalo.utility;

import java.util.UUID;

import net.minecraft.world.phys.Vec3;

public class SoulCage {

    public final UUID owner;
    public final Vec3 center;
    public final double radius;
    public int ticks;

    public SoulCage(UUID owner, Vec3 center, double radius, int ticks) {
        this.owner = owner;
        this.center = center;
        this.radius = radius;
        this.ticks = ticks;
    }
}