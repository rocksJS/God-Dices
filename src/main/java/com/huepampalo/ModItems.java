package com.huepampalo;

import com.huepampalo.HuepampaloMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class ModItems {

    public static final Item VENOM_PICKAXE = registerItem("venom_pickaxe", new VenomPickaxe());

    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(HuepampaloMod.MOD_ID, name), item);
    }

    public static void register() {
        System.out.println("Venom Items registered!");
    }
}