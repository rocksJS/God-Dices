package com.huepampalo.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SwordSparkManager {

    private static final List<SwordSpark> SPARKS = new ArrayList<>();
    private static final Random RANDOM = new Random();

    public static void spawn() {

        if (SPARKS.size() > 30)
            return;

        SPARKS.add(new SwordSpark(
                0.0F,
                1.0F, // сильно вверх
                -1.5F, // сильно назад (должно быть видно перед игроком)
                0,
                0.02F,
                0,
                0.15F, // огромный размер
                1.0F, 0.0F, 1.0F, 1.0F, // ярко-розовый
                40));
    }

    public static void tick() {

        Iterator<SwordSpark> iterator = SPARKS.iterator();

        while (iterator.hasNext()) {

            SwordSpark spark = iterator.next();

            if (!spark.tick()) {

                iterator.remove();

            }

        }

    }

    public static void render(
            PoseStack poseStack,
            MultiBufferSource buffer,
            int light) {

        poseStack.pushPose();
        poseStack.translate(2, -0.8, -0.5);

        // ... остальной код render без изменений ...

        // ... остальной render ...

        VertexConsumer consumer = buffer.getBuffer(RenderType.LINES);

        Matrix4f pose = poseStack.last().pose();

        for (SwordSpark spark : SPARKS) {

            float s = spark.size * 1.8f;
            float x = spark.x;
            float y = spark.y;
            float z = spark.z;

            float r = spark.red;
            float g = spark.green;
            float b = spark.blue;
            float a = spark.alpha;

            consumer.addVertex(pose, x - s, y, z)
                    .setColor(r, g, b, a)
                    .setUv(0, 0)
                    .setNormal(0, 1, 0);

            consumer.addVertex(pose, x + s, y, z)
                    .setColor(r, g, b, a)
                    .setUv(0, 0)
                    .setNormal(0, 1, 0);

            consumer.addVertex(pose, x, y - s, z)
                    .setColor(r, g, b, a)
                    .setUv(0, 0)
                    .setNormal(0, 1, 0);

            consumer.addVertex(pose, x, y + s, z)
                    .setColor(r, g, b, a)
                    .setUv(0, 0)
                    .setNormal(0, 1, 0);
        }

        poseStack.popPose(); // ← обязательно!
    }

}