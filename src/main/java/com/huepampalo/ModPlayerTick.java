package com.huepampalo;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.component.CustomData;
// import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ModPlayerTick {

    public static final ResourceLocation JUMP_SPEED_ID = ResourceLocation.fromNamespaceAndPath("huepampalo",
            "jump_speed_boost");

    public static void teleportWithGlitchesDice(ServerPlayer player) {

        // Get Dice Face
        int diceFace = (player.getMainHandItem().getItem() instanceof HuepampaloItem ? player.getMainHandItem()
                : player.getOffhandItem()).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag()
                .getInt("DiceFace");

        if (diceFace == 1) {
            // На всякий случай, чтобы не было нулевого множителя

            // if
            boolean isHoldingItem = player.getMainHandItem().getItem() instanceof HuepampaloItem
                    || player.getOffhandItem().getItem() instanceof HuepampaloItem;

            // Если игрок на земле — убираем бонус скорости
            if (player.onGround()) {
                cleanGlitches(player);
                return;
            }

            // === 1. Ускорение в воздухе (AttributeModifier) ===
            var attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
            if (attribute != null && !attribute.hasModifier(JUMP_SPEED_ID)) {
                AttributeModifier modifier = new AttributeModifier(
                        JUMP_SPEED_ID,
                        1.0,
                        AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
                attribute.addTransientModifier(modifier);
            }

            // === 2. Прямой разгон через DeltaMovement ===
            Vec3 motion = player.getDeltaMovement();
            double boost = 4.15;
            double newX = motion.x * boost;
            double newZ = motion.z * boost;
            player.setDeltaMovement(newX, motion.y, newZ);

            // === 3. Телепорт на точку, куда смотрит игрок (без кулдауна) ===
            if (isHoldingItem && player.fallDistance < 0.8) {

                // ItemStack stack = player.getMainHandItem().getItem() instanceof
                // HuepampaloItem
                // ? player.getMainHandItem()
                // : player.getOffhandItem();

                // Рейкаст
                BlockHitResult hit = (BlockHitResult) player.pick(6.5, 0, false);

                if (hit.getType() == HitResult.Type.BLOCK) {
                    Vec3 target = hit.getLocation();

                    // Телепортируем игрока
                    player.teleportTo(target.x, target.y + 1.0, target.z);
                    ModCounters.addTeleportCounter();
                    ModServerMessages.chatMessageAfterTeleport(player, ModCounters.getTeleportCounter());

                    // player.sendSystemMessage(Component.literal("Телепортации: " +
                    // teleportCount));

                    // Звук телепорта (можно убрать, если не нужен)
                    player.level().playSound(null, player.blockPosition(),
                            SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 0.6f, 1.4f);
                }

                // === 4. Эффект и опыт ===
                player.level().addFreshEntity(new ExperienceOrb(
                        player.level(), player.getX(), player.getY(), player.getZ(), 5));
            }
        }

        // Логгирование

        // тут логгирование сколько раз вызвана команда

    }

    public static void cleanGlitches(ServerPlayer player) {
        var attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attribute != null) {
            attribute.removeModifier(JUMP_SPEED_ID);
        }
    }
}