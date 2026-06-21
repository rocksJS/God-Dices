package com.huepampalo.client.mixin;

import com.huepampalo.ModPlayerTick;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @ModifyArg(method = "getFov", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;lerp(FFF)F"), index = 2)
    private float removeGlitchDiceEffect(float speedMultiplier) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player != null) {
            var attribute = mc.player.getAttribute(Attributes.MOVEMENT_SPEED);

            // Применяем дребезжание ТОЛЬКО если активен наш модификатор прыжка
            if (attribute != null && attribute.hasModifier(ModPlayerTick.JUMP_SPEED_ID)) {
                return 1.0F;
            }
        }

        return speedMultiplier; // В обычном состоянии — нормальное поведение
    }
}