package com.mmodding.library.block.api.wrapper;

import com.mmodding.library.block.api.util.BlockFactory;
import com.mmodding.library.block.impl.wrapper.BlockHeapImpl;
import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.java.api.function.Mapper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
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

	/**
	 * Creates a new {@link BlockHeap} from a block factory, shared settings and names.
	 * @param factory the block factory
	 * @param settings the block settings
	 * @param names the names
	 * @return the block heap
	 * @param <T> the block class type
	 */
	static <T extends Block> BlockHeap create(BlockFactory<T> factory, FabricBlockSettings settings, String... names) {
		return BlockHeap.create(factory, name -> name, name -> settings, names);
	}

	/**
	 * Creates a new {@link BlockHeap} from a block factory, shared settings and names.
	 * @param factory the block factory
	 * @param settings the block settings
	 * @param names the names
	 * @return the block heap
	 * @param <T> the block class type
	 */
	static <T extends Block> BlockHeap create(BlockFactory<T> factory, FabricBlockSettings settings, List<String> names) {
		return new BlockHeapImpl(factory, name -> name, name -> settings, names);
	}

	/**
	 * Creates a new {@link BlockHeap} from a block factory, a name mapper, a settings mapper, and names.
	 * @param factory the block factory
	 * @param nameMapper the name mapper
	 * @param settingsMapper the settings mapper
	 * @param names the names
	 * @return the block heap
	 * @param <T> the block class type
	 */
	static <T extends Block> BlockHeap create(BlockFactory<T> factory, AutoMapper<String> nameMapper, Mapper<String, FabricBlockSettings> settingsMapper, String... names)  {
		return BlockHeap.create(factory, nameMapper, settingsMapper, List.of(names));
	}

	/**
	 * Creates a new {@link BlockHeap} from a block factory, a name mapper, a settings mapper, and names.
	 * @param factory the block factory
	 * @param nameMapper the name mapper
	 * @param settingsMapper the settings mapper
	 * @param names the names
	 * @return the block heap
	 * @param <T> the block class type
	 */
	static <T extends Block> BlockHeap create(BlockFactory<T> factory, AutoMapper<String> nameMapper, Mapper<String, FabricBlockSettings> settingsMapper, List<String> names)  {
		return new BlockHeapImpl(factory, nameMapper, settingsMapper, names);
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
