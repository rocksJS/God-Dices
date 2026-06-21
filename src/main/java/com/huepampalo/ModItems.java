// package com.huepampalo;

// import net.minecraft.core.Registry;
// import net.minecraft.core.registries.BuiltInRegistries;
// import net.minecraft.world.item.Item;

// public class ModItems {

// // === Здесь объявляем все предметы ===
// public static final Item HUEPAMPALO_ITEM = new HuepampaloItem(
// new Item.Properties().stacksTo(1));

// // Пример будущего предмета:
// // public static final Item ANOTHER_ITEM = new AnotherItem(...);

// public static void register() {
// System.out.println("Регистрируем предмет huepampalo_item...");

// Registry.register(BuiltInRegistries.ITEM,
// HuepampaloMod.id("huepampalo_item"), HUEPAMPALO_ITEM);

// System.out.println("Предмет huepampalo_item успешно зарегистрирован!");

// // Здесь будешь добавлять новые предметы:
// // Registry.register(BuiltInRegistries.ITEM,
// HuepampaloMod.id("another_item"),
// // ANOTHER_ITEM);
// }
// }