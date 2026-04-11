package com.mmodding.library.block.api;

import com.mmodding.library.core.api.management.info.InjectedContent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

@ApiStatus.NonExtendable
@InjectedContent(Block.class)
public interface BlockWithItem {

	default <T extends Block> T withItem() {
		return this.withItem(new Item.Properties(), BlockItem::new);
	}

	default <T extends Block> T withItem(@NotNull Item.Properties settings) {
		return this.withItem(settings, BlockItem::new);
	}

	default <T extends Block> T withItem(@NotNull Item.Properties settings, @NotNull Function<Item, Item> tweaker) {
		return this.withItem(settings, BlockItem::new, tweaker);
	}

	default <T extends Block> T withItem(@NotNull Item.Properties settings, @NotNull BiFunction<T, Item.Properties, Item> factory) {
		return this.withItem(settings, factory, item -> item);
	}

	default <T extends Block> T withItem(@NotNull Item.Properties settings, @NotNull BiFunction<T, Item.Properties, Item> factory,  @NotNull Function<Item, Item> tweaker) {
		throw new IllegalStateException();
	}
}
