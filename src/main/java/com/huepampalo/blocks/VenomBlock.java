package com.huepampalo.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

public class VenomBlock extends Block {

    public VenomBlock() {
        super(Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .strength(2.5f, 8.0f) // довольно прочный
                .sound(SoundType.STONE));
    }
}