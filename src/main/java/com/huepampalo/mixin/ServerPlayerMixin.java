package com.huepampalo.mixin;

import com.huepampalo.HuepampaloItem;
import com.huepampalo.ModPlayerTick;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    @Inject(method = "doTick", at = @At("HEAD"))
    private void onPlayerTick(CallbackInfo ci) {

        ServerPlayer player = (ServerPlayer) (Object) this;

        // Бонус здоровья должен обновляться каждый тик
        ModPlayerTick.tickBonusHealth(player);

        boolean isHoldingItem = player.getMainHandItem().getItem() instanceof HuepampaloItem
                || player.getOffhandItem().getItem() instanceof HuepampaloItem;

        boolean isInAir = !player.onGround();

        if (isHoldingItem && isInAir) {
            ModPlayerTick.addTickGliches(player);
        } else {
            ModPlayerTick.cleanGlitches(player);
        }
    }
}