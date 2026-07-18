package com.huepampalo.client;

public class ClientSoulData {

    private static float soulHealth = 0;

    public static void setSoulHealth(float value) {
        soulHealth = value;
    }

    public static float getSoulHealth() {
        return soulHealth;
    }
}