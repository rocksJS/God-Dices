package com.huepampalo.blocks;

import com.huepampalo.HuepampaloMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModBlocks {

    public static final Block VENOM_BLOCK = registerBlock("venom_block", new VenomBlock());

    private static Block registerBlock(String name, Block block) {
        Registry.register(BuiltInRegistries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(HuepampaloMod.MOD_ID, name), block);

        Registry.register(BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(HuepampaloMod.MOD_ID, name),
                new BlockItem(block, new Item.Properties()));

        return block;
    }

    public static void register() {
        System.out.println("Venom Blocks registered!");
    }
}