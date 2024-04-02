package com.mmodding.library.block.api;

import com.mmodding.library.block.impl.BlockWithItemImpl;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

@ApiStatus.NonExtendable
public interface BlockWithItem {

	static Item getItem(Block block) {
		return BlockWithItemImpl.getItem(block);
	}

	default <T extends Block> T withItem(@NotNull Item.Settings settings) {
		return this.withItem(settings, BlockItem::new);
	}

	default <T extends Block> T withItem(@NotNull Item.Settings settings, @NotNull BiFunction<T, Item.Settings, Item> factory) {
		throw new IllegalStateException();
	}
}
