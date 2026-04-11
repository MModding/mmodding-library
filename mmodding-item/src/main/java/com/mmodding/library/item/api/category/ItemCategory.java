package com.mmodding.library.item.api.category;

import com.mmodding.library.item.impl.category.ItemCategoryImpl;
import org.jetbrains.annotations.ApiStatus;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

@ApiStatus.NonExtendable
public interface ItemCategory {

	static ItemCategory create(ResourceKey<CreativeModeTab> key, Consumer<Settings> settings) {
		return new ItemCategoryImpl(key, settings);
	}

	ResourceKey<CreativeModeTab> getRegistryKey();

	Optional<CreativeModeTab> getCreativeModeTab();

	@ApiStatus.NonExtendable
	interface Settings {

		Settings name(Component name);

		Settings icon(Supplier<ItemStack> iconSupplier);

		Settings special();

		Settings hideName();

		Settings hideScrollbar();

		Settings backgroundTextureName(String textureName);
	}
}
