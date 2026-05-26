package com.mmodding.library.block.api;

import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface BlockWithItem {

	default <T extends Block> T registerItem() {
		return this.registerItem(BlockItem::new, new Item.Properties());
	}

	default <T extends Block> T registerItem(@NotNull Item.Properties properties) {
		return this.registerItem(BlockItem::new, properties);
	}

	default <T extends Block> T registerItem(@NotNull Item.Properties properties, @NotNull Function<Item, Item> tweaker) {
		return this.registerItem(BlockItem::new, properties, tweaker);
	}

	default <T extends Block> T registerItem(@NotNull BiFunction<T, Item.Properties, Item> factory, @NotNull Item.Properties properties) {
		return this.registerItem(factory, properties, item -> item);
	}

	default <T extends Block> T registerItem(@NotNull BiFunction<T, Item.Properties, Item> factory, @NotNull Item.Properties properties, @NotNull Function<Item, Item> tweaker) {
		throw new IllegalStateException();
	}
}
