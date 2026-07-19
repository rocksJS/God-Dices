package com.huepampalo.client.render;

public class SwordSpark {

    public float x;
    public float y;
    public float z;

    public float motionX;
    public float motionY;
    public float motionZ;

    public float size;

    public float red;
    public float green;
    public float blue;
    public float alpha;

    public int age;
    public int maxAge;

    public SwordSpark(
            float x,
            float y,
            float z,
            float motionX,
            float motionY,
            float motionZ,
            float size,
            float red,
            float green,
            float blue,
            float alpha,
            int maxAge) {

        this.x = x;
        this.y = y;
        this.z = z;

        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;

        this.size = size;

        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;

        this.maxAge = maxAge;
        this.age = maxAge;
    }

    public boolean tick() {

        x += motionX;
        y += motionY;
        z += motionZ;

        motionX *= 0.96F;
        motionY *= 0.96F;
        motionZ *= 0.96F;

        age--;

        alpha = age / (float) maxAge;

        return age > 0;
    }
}