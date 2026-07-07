package com.huepampalo.mixin;

import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Wolf.class)
public abstract class WolfMixin {

    // private int pickupCooldown = 0;

    // @Inject(method = "aiStep", at = @At("HEAD"), cancellable = false)
    // private void onAiStep(CallbackInfo ci) {
    // Wolf wolf = (Wolf) (Object) this;

    // if (pickupCooldown > 0) {
    // pickupCooldown--;
    // return;
    // }

    // // Проверяем только если волк tame, не сидит и руки пустые
    // if (wolf.isTame() && !wolf.isOrderedToSit() &&
    // wolf.getMainHandItem().isEmpty()) {
    // for (ItemEntity itemEntity :
    // wolf.level().getEntitiesOfClass(ItemEntity.class,
    // wolf.getBoundingBox().inflate(3.5))) {
    // if (itemEntity.isAlive() && !itemEntity.hasPickUpDelay()) {
    // ItemStack stack = itemEntity.getItem().copy();

    // wolf.setItemSlot(net.minecraft.world.entity.EquipmentSlot.MAINHAND, stack);
    // itemEntity.discard();

    // wolf.playSound(net.minecraft.sounds.SoundEvents.ENDERMAN_TELEPORT, 0.6f,
    // 1.3f);

    // pickupCooldown = 30; // ~1.5 секунды
    // return;
    // }
    // }
    // }
    // }

    // @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    // private void onMobInteract(Player player, net.minecraft.world.InteractionHand
    // hand,
    // CallbackInfoReturnable<net.minecraft.world.InteractionResult> cir) {
    // // Позволяем обычному взаимодействию работать
    // }
}