package com.huepampalo;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class DarkSister extends SwordItem {

    public DarkSister() {
        super(Tiers.NETHERITE,
                new Properties()
                        .durability(2500) // очень прочная

                        .rarity(net.minecraft.world.item.Rarity.EPIC).attributes(SwordItem
                                .createAttributes(Tiers.NETHERITE, 20, 1)));
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        if (!(attacker instanceof Player player)) {
            return super.hurtEnemy(stack, target, attacker);
        }

        double radius = 3.0;

        AABB box = player.getBoundingBox().inflate(radius);

        Vec3 look = player.getLookAngle().normalize();

        for (LivingEntity entity : player.level().getEntitiesOfClass(LivingEntity.class, box)) {

            if (entity == player)
                continue;

            Vec3 toEntity = entity.position()
                    .subtract(player.position())
                    .normalize();

            double angle = look.dot(toEntity);

            // 🎯 это "дуга удара"
            // 0.45 ~ 65 градусов
            if (angle > 0.45) {

                entity.hurt(player.damageSources().playerAttack(player), 7.0F);

                entity.addEffect(new MobEffectInstance(
                        MobEffects.MOVEMENT_SLOWDOWN,
                        40,
                        1));
            }
        }

        return super.hurtEnemy(stack, target, attacker);
    }

}