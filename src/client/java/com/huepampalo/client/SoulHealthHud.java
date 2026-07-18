package com.huepampalo.client;

import com.huepampalo.DarkSister;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class SoulHealthHud {

    public static void register() {

        HudRenderCallback.EVENT.register(
                (guiGraphics, tickDelta) -> {

                    Minecraft mc = Minecraft.getInstance();

                    if (mc.player == null)
                        return;

                    float soul = ClientSoulData.getSoulHealth();

                    if (soul <= 0)
                        return;

                    drawSoulHearts(
                            guiGraphics,
                            soul);
                });
    }

    private static void drawSoulHearts(
            GuiGraphics guiGraphics,
            float soul) {

        Minecraft mc = Minecraft.getInstance();

        int x = mc.getWindow().getGuiScaledWidth() / 2
                - 91;

        int y = mc.getWindow().getGuiScaledHeight()
                - 49;

        int hearts = (int) Math.ceil(soul / 2);

        for (int i = 0; i < hearts; i++) {

            guiGraphics.drawString(
                    mc.font,
                    "💜",
                    x + i * 9,
                    y,
                    0xAA00FF);
        }
    }
}