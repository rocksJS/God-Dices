package com.huepampalo.client;

import com.huepampalo.ModItems;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public class HuepampaloModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as
		// rendering.
		// BlockRenderLayerMap.INSTANCE.putItem(
		// ModItems.DARK_SISTER,
		// RenderType.isFoil());
		ClientNetworking.register();
		SoulHealthHud.register();
	}
}

// to remove