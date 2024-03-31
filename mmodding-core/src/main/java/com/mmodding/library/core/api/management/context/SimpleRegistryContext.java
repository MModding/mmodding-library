package com.mmodding.library.core.api.management.context;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class SimpleRegistryContext<T> extends RegistryContext {

	public static final SimpleRegistryContext<Block> BLOCK = new SimpleRegistryContext<>(RegistryKeys.BLOCK);
	public static final SimpleRegistryContext<Item> ITEM = new SimpleRegistryContext<>(RegistryKeys.ITEM);

	public SimpleRegistryContext(RegistryKey<Registry<T>> registryKey) {
		super(registryKey);
	}
}
