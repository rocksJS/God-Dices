package com.huepampalo;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import java.util.Random;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class HuepampaloItem extends Item {

    public HuepampaloItem(Properties properties) {
        super(properties);
    }

    // USE
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {

            // === Добавляем модификатор скорости (убираем замедление + даём ускорение) ===
            AttributeModifier speedModifier = new AttributeModifier(
                    ResourceLocation.fromNamespaceAndPath("huepampalo", "movement_speed"),
                    6.7, // добавляем +200% к скорости
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL //
            );
            var attribute = serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED);
            if (attribute != null && !attribute.hasModifier(speedModifier.id())) {
                attribute.addTransientModifier(speedModifier);
            }
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int remainingUseDuration) {

        if (!level.isClientSide && entity instanceof ServerPlayer serverPlayer) {

            var attribute = serverPlayer.getAttribute(Attributes.MOVEMENT_SPEED);
            if (attribute != null) {
                attribute.removeModifier(ResourceLocation.fromNamespaceAndPath("huepampalo", "movement_speed"));
            }

            // === 1. Убираем эффект, который был наложен при прицеливании ===

            serverPlayer.removeEffect(MobEffects.MOVEMENT_SPEED);

            // === 2. Основная логика при отпускании ПКМ ===
            CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            int currentFace = data.copyTag().getInt("DiceFace");
            if (currentFace == 0)
                currentFace = 1;

            // Рандомная грань
            int newFace = new Random().nextInt(6) + 1;

            // Сохраняем новую грань
            CompoundTag tag = data.copyTag();
            tag.putInt("DiceFace", newFace);
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
            stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(newFace));

            // Эффекты
            serverPlayer.setHealth(serverPlayer.getMaxHealth());
            serverPlayer.sendSystemMessage(Component.literal("§aКости показали: §e" + newFace));

            // Звук
            BlockHitResult hitResult = (BlockHitResult) serverPlayer.pick(5.0D, 0.0F, false);
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos targetPos = hitResult.getBlockPos();
                level.playSound(null, targetPos, SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.5F);
            }

            attribute.removeModifier(ResourceLocation.fromNamespaceAndPath("huepampalo", "movement_speed"));
        }
    }
}