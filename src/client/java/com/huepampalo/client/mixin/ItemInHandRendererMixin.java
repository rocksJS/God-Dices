package com.huepampalo.client.mixin;

import com.huepampalo.DarkSister;
import com.huepampalo.HuepampaloItem;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    private static final Random RANDOM = new Random();

    private static boolean darkSisterSwingStarted = false;

    private static boolean darkSisterActive = false;

    private static float darkSisterTimer = 0F;

    private static int currentDarkSisterAnimation = 0;

    // ПКМ стойка
    private static boolean darkSisterUsing = false;

    private static float darkSisterUseTime = 0F;

    @Inject(method = "renderArmWithItem", at = @At("HEAD"))
    private void onRenderArmWithItem(
            AbstractClientPlayer player,
            float partialTicks,
            float pitch,
            InteractionHand hand,
            float swingProgress,
            ItemStack stack,
            float equippedProgress,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int combinedLight,
            CallbackInfo ci) {

        /*
         * HUEPAMPALO ITEM
         */

        if (stack.getItem() instanceof HuepampaloItem
                && player.isUsingItem()) {

            float useTicks = player.getUseItemRemainingTicks();

            float time = (72000 - useTicks + partialTicks) * 0.5F;

            poseStack.mulPose(
                    Axis.YP.rotationDegrees(
                            time * 45F));

            poseStack.mulPose(
                    Axis.XP.rotationDegrees(
                            (float) Math.sin(time * 0.3F) * 8F));

            poseStack.translate(
                    0,
                    (float) Math.sin(time * 1.8F) * 0.08F,
                    0);

            poseStack.mulPose(
                    Axis.XP.rotationDegrees(15));
        }

        /*
         * DARK SISTER
         */

        if (stack.getItem() instanceof DarkSister
                && hand == InteractionHand.MAIN_HAND) {

            /*
             * ПКМ - стойка катаны
             */

            if (player.isUsingItem()) {

                if (!darkSisterUsing) {

                    darkSisterUsing = true;
                    darkSisterUseTime = 0F;
                }

                darkSisterUseTime += 0.05F;

                if (darkSisterUseTime > 1F) {

                    darkSisterUseTime = 1F;
                }

                float charge = (float) Math.sin(
                        darkSisterUseTime *
                                Math.PI *
                                0.5F);

                /*
                 * уход назад
                 */

                poseStack.translate(
                        0,
                        0.05F * charge,
                        0.35F * charge);

                /*
                 * подготовка разворота
                 */

                poseStack.mulPose(
                        Axis.YP.rotationDegrees(
                                90F * charge));

                poseStack.mulPose(
                        Axis.ZP.rotationDegrees(
                                -25F * charge));

                /*
                 * энергия клинка
                 */

                float shake = (float) Math.sin(
                        darkSisterUseTime * 20F)
                        * 2F
                        * charge;

                poseStack.mulPose(
                        Axis.XP.rotationDegrees(
                                shake));

            } else {

                if (darkSisterUsing) {

                    darkSisterUsing = false;
                    darkSisterUseTime = 0F;
                }
            }

            /*
             * начало атаки
             */

            if (swingProgress > 0
                    && !darkSisterSwingStarted) {

                currentDarkSisterAnimation = RANDOM.nextInt(4);

                String animationName = switch (currentDarkSisterAnimation) {

                    case 0 -> "Прокол";
                    case 1 -> "Иайдо: Рассечение";
                    case 2 -> "Теневой вихрь";
                    case 3 -> "Небесный разлом";

                    default -> "Неизвестно";
                };

                player.sendSystemMessage(
                        Component.literal(
                                "§5DarkSister атака: §f"
                                        + animationName));

                darkSisterSwingStarted = true;

                if (currentDarkSisterAnimation == 0) {

                    darkSisterActive = true;
                    darkSisterTimer = 0F;

                } else {

                    darkSisterActive = false;
                }
            }

            /*
             * ПРОКОЛ
             */

            if (darkSisterActive) {

                darkSisterTimer += 0.025F;

                if (darkSisterTimer > 1F)
                    darkSisterTimer = 1F;

                float progress = darkSisterTimer;

                float draw = 0F;

                float strike = 0F;

                if (progress < 0.35F)

                    draw = progress / 0.35F;

                if (progress > 0.35F)

                    strike = (progress - 0.35F)
                            / 0.65F;

                draw = (float) Math.sin(
                        draw *
                                Math.PI *
                                0.5F);

                strike = (float) Math.sin(
                        strike *
                                Math.PI);

                poseStack.mulPose(
                        Axis.YP.rotationDegrees(
                                180F * draw));

                poseStack.translate(
                        0,
                        0,
                        -1.2F * strike);

                if (darkSisterTimer >= 1F) {

                    darkSisterActive = false;
                    darkSisterSwingStarted = false;
                    darkSisterTimer = 0F;
                }
            }

            /*
             * остальные атаки
             */

            if (!darkSisterActive
                    && swingProgress > 0) {

                float swing = (float) Math.sin(
                        swingProgress *
                                Math.PI);

                float power = swing * swing;

                switch (currentDarkSisterAnimation) {

                    /*
                     * ИАЙДО
                     */

                    case 1 -> {

                        poseStack.translate(
                                0.25F * swing,
                                -0.05F * swing,
                                0.15F * swing);

                        poseStack.mulPose(
                                Axis.YP.rotationDegrees(
                                        -45F * swing));

                        poseStack.mulPose(
                                Axis.ZP.rotationDegrees(
                                        -150F * power));

                        poseStack.translate(
                                0,
                                0,
                                -0.45F * power);
                    }

                    /*
                     * ТЕНЕВОЙ ВИХРЬ
                     */

                    case 2 -> {

                        float spin = swing * swing;

                        poseStack.mulPose(
                                Axis.YP.rotationDegrees(
                                        300F * spin));

                        poseStack.mulPose(
                                Axis.ZP.rotationDegrees(
                                        -80F * swing));

                        poseStack.translate(
                                -0.3F * swing,
                                0,
                                -0.45F * power);

                        poseStack.translate(
                                0,
                                0.15F * swing,
                                0);
                    }

                    /*
                     * НЕБЕСНЫЙ РАЗЛОМ
                     */

                    case 3 -> {

                        poseStack.translate(
                                0,
                                0.35F * swing,
                                0.1F * swing);

                        poseStack.mulPose(
                                Axis.XP.rotationDegrees(
                                        -190F * power));

                        poseStack.mulPose(
                                Axis.YP.rotationDegrees(
                                        35F * swing));

                        poseStack.mulPose(
                                Axis.ZP.rotationDegrees(
                                        60F * swing));

                        poseStack.translate(
                                0,
                                0,
                                -0.55F * power);
                    }
                }
            }

            if (swingProgress == 0
                    && !darkSisterActive) {

                darkSisterSwingStarted = false;
            }
        }
    }
}