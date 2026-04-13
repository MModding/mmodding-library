package com.mmodding.library.enchantment.api;

import com.mmodding.library.enchantment.impl.family.EnchantmentLinkRegistryImpl;
import net.minecraft.world.item.Item;

/**
 * Makes enchanted books and their source item have the right logic on vanilla systems.
 */
public class EnchantmentLinkRegistry {

	public EnchantmentLinkRegistry() {
		throw new IllegalStateException("Configs class only contains static definitions");
	}

	/**
	 * Acknowledges the modded enchanted book item as one and the book item as its source.
	 * @param bookItem the book item
	 * @param enchantedBookItem the enchanted book item
	 */
	public static void registerLink(Item bookItem, Item enchantedBookItem) {
		EnchantmentLinkRegistryImpl.registerLink(bookItem, enchantedBookItem);
	}
}
