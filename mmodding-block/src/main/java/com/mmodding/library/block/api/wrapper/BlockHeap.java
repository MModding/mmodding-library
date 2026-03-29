package com.mmodding.library.block.api.wrapper;

import com.mmodding.library.block.api.util.AdvancedBlockFactory;
import com.mmodding.library.block.api.util.BlockFactory;
import com.mmodding.library.block.impl.wrapper.BlockHeapImpl;
import com.mmodding.library.java.api.function.AutoMapper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A simple class allowing to create and manipulate a bunch of blocks at the same time.
 */
public interface BlockHeap {

	static <T extends Block> BlockHeap create(Identifier identifier, BlockFactory<T> factory, FabricBlockSettings settings, String... names) {
		return BlockHeap.create(identifier, AdvancedBlockFactory.of(factory), settings, List.of(names));
	}

	static <T extends Block> BlockHeap create(Identifier identifier, BlockFactory<T> factory, FabricBlockSettings settings, List<String> names) {
		return BlockHeap.create(identifier, AdvancedBlockFactory.of(factory), settings, names);
	}

	static <T extends Block> BlockHeap create(Identifier identifier, AdvancedBlockFactory<T> factory, FabricBlockSettings settings, String... names) {
		return new BlockHeapImpl(identifier, factory, settings, List.of(names));
	}

	static <T extends Block> BlockHeap create(Identifier identifier, AdvancedBlockFactory<T> factory, FabricBlockSettings settings, List<String> names) {
		return new BlockHeapImpl(identifier, factory, settings, names);
	}

	default BlockHeap withItem() {
		return this.withItem(new Item.Settings(), BlockItem::new);
	}

	default BlockHeap withItem(@NotNull Item.Settings settings) {
		return this.withItem(settings, BlockItem::new);
	}

	default BlockHeap withItem(@NotNull Item.Settings settings, @NotNull Function<Item, Item> tweaker) {
		return this.withItem(settings, BlockItem::new, tweaker);
	}

	default BlockHeap withItem(@NotNull Item.Settings settings, @NotNull BiFunction<Block, Item.Settings, Item> factory) {
		return this.withItem(settings, factory, item -> item);
	}

	BlockHeap withItem(@NotNull Item.Settings settings, @NotNull BiFunction<Block, Item.Settings, Item> factory,  @NotNull Function<Item, Item> tweaker);

	TagKey<Block> getBlockTagKey();

	TagKey<Item> getItemTagKey();

	List<Block> getEntries();

	BlockHeap map(AutoMapper<Block> mapper);

	void forEach(Consumer<Block> consumer);

	/**
	 * Registers every block inside the heap.
	 * @param identifierMaker the identifier maker turning the heap's block string names into usable identifiers for registration
	 */
	void register(Function<String, Identifier> identifierMaker);

	@Environment(EnvType.CLIENT)
	void cutout();

	@Environment(EnvType.CLIENT)
	void translucent();
}
