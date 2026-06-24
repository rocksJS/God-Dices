package com.huepampalo;

import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;

public class VenomPickaxe extends PickaxeItem {

    public VenomPickaxe() {
        super(Tiers.NETHERITE,
                new Properties()
                        .durability(2500) // очень прочная
                        .rarity(net.minecraft.world.item.Rarity.EPIC));
    }
}