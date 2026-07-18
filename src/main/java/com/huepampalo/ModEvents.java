package com.huepampalo;

import com.huepampalo.classes.SoulCage;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public class ModEvents {

    public static void register() {

        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {

            if (!(entity.level() instanceof ServerLevel level))
                return;

            for (SoulCage cage : DarkSister.SOUL_CAGES) {

                if (entity.position().distanceTo(cage.center) <= cage.radius) {

                    Player owner = level.getPlayerByUUID(cage.owner);

                    if (owner != null) {

                        owner.heal(4);

                        DarkSister.bonusHealth.merge(
                                owner.getUUID(),
                                (double) entity.getMaxHealth(),
                                Double::sum);

                        // Апдейт таймер (10 секунд = 200 тиков)
                        DarkSister.bonusHealthTimer.put(
                                owner.getUUID(),
                                200);

                        level.sendParticles(
                                ParticleTypes.GLOW,
                                owner.getX(),
                                owner.getY() + 1,
                                owner.getZ(),
                                3,
                                0.3,
                                0.3,
                                0.3,
                                0);
                    }

                    break;
                }
            }
        });
    }
}