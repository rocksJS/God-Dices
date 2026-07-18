package com.huepampalo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.component.CustomData;
// import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ModPlayerTick {

    public static final Map<LivingEntity, Integer> delayedHits = new HashMap<>();

    public static final ResourceLocation TELEPORT_GLITCHES_ID = ResourceLocation.fromNamespaceAndPath("huepampalo",
            "jump_speed_boost");

    public static final ResourceLocation BONUS_HEALTH_ID = ResourceLocation.fromNamespaceAndPath(
            "huepampalo",
            "dark_sister_bonus_health");

    // public static void tickDelayedHits(ServerPlayer player) {

    // Iterator<Map.Entry<LivingEntity, Integer>> it =
    // delayedHits.entrySet().iterator();

    // while (it.hasNext()) {

    // var entry = it.next();
    // LivingEntity entity = entry.getKey();
    // int ticks = entry.getValue() - 1;

    // if (ticks <= 0) {

    // if (entity != null && entity.isAlive() && !entity.isRemoved()) {
    // entity.hurt(
    // entity.level().damageSources().generic(),
    // 20.0F);
    // }

    // it.remove();

    // } else {
    // entry.setValue(ticks);
    // }
    // }

    // // ModServerInteractions.chatMessageAfterTeleport(player, 0, "jalup");

    // // Задержка второго удара для катаны, нужно переписатьыы
    // }

    public static void addTickGliches(ServerPlayer player) {

        // Get Dice Face
        int diceFace = (player.getMainHandItem().getItem() instanceof HuepampaloItem ? player.getMainHandItem()
                : player.getOffhandItem()).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag()
                .getInt("DiceFace");

        // Проверка перед входом в цикл телепорта
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
            if (attribute != null && !attribute.hasModifier(TELEPORT_GLITCHES_ID)) {
                AttributeModifier modifier = new AttributeModifier(
                        TELEPORT_GLITCHES_ID,
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

                    // Блок с шансами телепортации (10% — вверх, 90% — на точку)
                    if (new java.util.Random().nextInt(100) < 10) {
                        performRandomTeleport(player, target);

                    } else {
                        // 90% шанс — обычный телепорт на блок, куда смотришь
                        player.teleportTo(target.x, target.y + 1.0, target.z);
                        ModCounters.addTeleportCounter();
                        ModServerInteractions.chatMessageAfterTeleport(player, ModCounters.getTeleportCounter());
                    }
                    // Телепортируем игрока
                    // player.teleportTo(target.x, target.y + 1.0, target.z);
                    // ModCounters.addTeleportCounter();
                    // ModServerMessages.chatMessageAfterTeleport(player,
                    // ModCounters.getTeleportCounter());

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

        if (diceFace == 4) {

            // Теперь на 4 уничтожаются блоки когда прыгаешь
            // if
            boolean isHoldingItem = player.getMainHandItem().getItem() instanceof HuepampaloItem
                    || player.getOffhandItem().getItem() instanceof HuepampaloItem;

            // Если игрок на земле — убираем каждотиковый глитч
            if (player.onGround()) {
                cleanGlitches(player);
                return;
            }

            BlockHitResult hit = (BlockHitResult) player.pick(6.5, 0, false);
            if (hit.getType() == HitResult.Type.BLOCK) {
                player.level().destroyBlock(hit.getBlockPos(), isHoldingItem);
            }

        }

        // Логгирование

        // тут логгирование сколько раз вызвана команда

    }

    private static void performRandomTeleport(ServerPlayer player, Vec3 target) {
        java.util.Random random = new java.util.Random();
        int chance = random.nextInt(100);

        // 2% шанс — телепорт вверх на 100 блоков
        if (chance < 2) {
            player.teleportTo(player.getX(), player.getY() + 100, player.getZ());
            ModCounters.addTeleportCounter();
            ModServerInteractions.chatMessageAfterTeleport(player, ModCounters.getTeleportCounter(),
                    "Где я?!");
        }

        if (chance >= 2 && chance < 5 || ModCounters.getTeleportCounter() > 100) {
            // 3% шанс при 100+ — телепорт вниз на 50 блоков
            player.teleportTo(player.getX(), player.getY() - 50, player.getZ());
            ModCounters.addTeleportCounter();
            ModServerInteractions.chatMessageAfterTeleport(player, ModCounters.getTeleportCounter(),
                    "Ты доигрался... Ничего не проходит бесследно.");
        } else {
            // 90% шанс — обычный телепорт на блок
            player.teleportTo(target.x, target.y + 1.0, target.z);
        }

    }

    public static void tickBonusHealth(ServerPlayer player) {

        UUID id = player.getUUID();

        if (!DarkSister.bonusHealthTimer.containsKey(id)) {
            return;
        }

        AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (attribute == null)
            return;

        // Уменьшаем только таймер
        int timer = DarkSister.bonusHealthTimer.get(id) - 1;

        // Таймер закончился
        if (timer <= 0) {

            // Удаляем бонус
            DarkSister.bonusHealth.remove(id);
            DarkSister.bonusHealthTimer.remove(id);

            // Удаляем модификатор максимального здоровья
            attribute.removeModifier(BONUS_HEALTH_ID);

            // Если текущее здоровье больше нового максимума,
            // ограничиваем его новым максимумом
            float newMaxHealth = (float) attribute.getValue();

            if (player.getHealth() > newMaxHealth) {
                player.setHealth(newMaxHealth);
            }

            return;
        }

        // Сохраняем обновлённый таймер
        DarkSister.bonusHealthTimer.put(id, timer);

        // Обновляем модификатор максимального здоровья
        attribute.removeModifier(BONUS_HEALTH_ID);

        double bonus = DarkSister.bonusHealth.getOrDefault(id, 0.0);

        if (bonus > 0) {
            attribute.addTransientModifier(
                    new AttributeModifier(
                            BONUS_HEALTH_ID,
                            bonus,
                            AttributeModifier.Operation.ADD_VALUE));
        }
    }

    public static void tickSoulHealth(ServerPlayer player) {

        if (player.tickCount % 10 != 0)
            return;

        UUID id = player.getUUID();

        float soul = DarkSister.soulHealth.getOrDefault(id, 0F);

        if (soul > 0) {

            soul -= 1;

            if (soul <= 0) {
                DarkSister.soulHealth.remove(id);
            } else {
                DarkSister.soulHealth.put(id, soul);
            }
        }
    }

    public static void cleanGlitches(ServerPlayer player) {
        var attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attribute != null) {
            attribute.removeModifier(TELEPORT_GLITCHES_ID);
        }
    }
}