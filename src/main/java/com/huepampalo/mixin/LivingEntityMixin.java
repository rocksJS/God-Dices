package com.huepampalo.mixin;

import com.huepampalo.DarkSister;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.world.damagesource.DamageSource;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void absorbSoulDamage(
            DamageSource source,
            float amount,
            CallbackInfoReturnable<Boolean> cir) {

        LivingEntity entity = (LivingEntity) (Object) this;

        if (!(entity instanceof net.minecraft.world.entity.player.Player player))
            return;

        float soul = DarkSister.soulHealth.getOrDefault(
                player.getUUID(),
                0F);

        if (soul <= 0)
            return;

        if (amount <= soul) {

            DarkSister.soulHealth.put(
                    player.getUUID(),
                    soul - amount);

            cir.setReturnValue(false);
        }

        else {

            float damageLeft = amount - soul;

            DarkSister.soulHealth.remove(
                    player.getUUID());

            player.setHealth(
                    player.getHealth() - damageLeft);

            cir.setReturnValue(false);
        }
    }
}