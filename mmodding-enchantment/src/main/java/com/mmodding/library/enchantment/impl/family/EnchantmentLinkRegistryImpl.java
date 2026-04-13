package com.mmodding.library.enchantment.impl.family;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;

@ApiStatus.Internal
public class EnchantmentLinkRegistryImpl {

	private static final Map<Item, Item> SOURCE_TO_ENCHANTED = new Object2ObjectOpenHashMap<>();
	private static final Map<Item, Item> ENCHANTED_TO_SOURCE = new Object2ObjectOpenHashMap<>();

	public static void registerLink(Item bookItem, Item enchantedBookItem) {
		SOURCE_TO_ENCHANTED.put(bookItem, enchantedBookItem);
		ENCHANTED_TO_SOURCE.put(enchantedBookItem, bookItem);
	}

	public static boolean isSource(ItemStack bookItemStack) {
		return SOURCE_TO_ENCHANTED.containsKey(bookItemStack.getItem());
	}

	public static boolean isEnchanted(ItemStack enchantedBookItemStack) {
		return ENCHANTED_TO_SOURCE.containsKey(enchantedBookItemStack.getItem());
	}

	public static Item getEnchanted(ItemStack bookItemStack) {
		return SOURCE_TO_ENCHANTED.get(bookItemStack.getItem());
	}

	public static Item getSource(ItemStack enchantedBookItemStack) {
		return ENCHANTED_TO_SOURCE.get(enchantedBookItemStack.getItem());
	}
}
