package com.mmodding.library.item.api.util;

import net.minecraft.world.item.Item;

@FunctionalInterface
public interface ItemFactory<T extends Item> {

	T make(Item.Properties properties);
}
