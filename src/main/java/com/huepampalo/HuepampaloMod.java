package com.huepampalo;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public class HuepampaloMod implements ModInitializer {
	public static final String MOD_ID = "huepampalo-mod";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Item HUEPAMPALO_ITEM = new HuepampaloItem(
			new Item.Properties().stacksTo(1));

	@Override
	public void onInitialize() {
		// System.out.println("Хуепампало!");

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			// Создание готовой команды "huepampalo", которая восстанавливает здоровье
			// игрока и отправляет сообщение в чат.
			dispatcher.register(
					net.minecraft.commands.Commands.literal("huepampalo")
							.executes(context -> {
								if (context.getSource().getEntity() instanceof Player player) {
									player.setHealth(player.getMaxHealth());
									player.sendSystemMessage(Component.literal("Хуепампало веном!"));
									return 1;
								}
								return 0;
							}));
		});

		// Регистрация предмета "huepampalo_item" в реестре Minecraft
		Registry.register(
				BuiltInRegistries.ITEM,
				id("huepampalo_item"),
				HUEPAMPALO_ITEM);
		// ModPlayerTick.register();
	}

	// CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess,
	// environment) -> {

	// // dispatcherl

	// dispatcher.register(
	// CommandManager.literal("huepampalo")
	// .executes(context -> {
	// if (context.getSource().getEntity() instanceof ServerPlayerEntity player) {
	// player.setHealth(player.getMaxHealth());
	// player.sendMessage(Text.literal("§aПолучено полное здоровье!"), false);
	// return 1;
	// }
	// return 0;
	// })
	// );

	// Player.
	// Player.class.("Хуепампало!", false);

	// context.

	// player.huepampalo();

	// This code runs as soon as Minecraft is in a mod-load-ready state.
	// However, some things (like resources) may still be uninitialized.
	// Proceed with mild caution.

	// Русский язык и это перевод

	// LOGGER.info("Hello Fabric world!");

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}
