package com.mmodding.library.block.api.wrapper;

import com.mmodding.library.block.api.util.BlockFactory;
import com.mmodding.library.block.impl.wrapper.BlockHeapImpl;
import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.java.api.function.Mapper;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A simple class allowing to create and manipulate a bunch of blocks at the same time.
 */
public interface BlockHeap {

	/**
	 * Creates a new {@link BlockHeap} from a block factory, shared properties and names.
	 * @param factory the block factory
	 * @param properties the block properties
	 * @param namespace the namespace
	 * @param names the names
	 * @return the block heap
	 * @param <T> the block class type
	 */
	static <T extends Block> BlockHeap register(BlockFactory<T> factory, Supplier<BlockBehaviour.Properties> properties, String namespace, String... names) {
		return BlockHeap.register(factory, name -> name, name -> properties.get(), namespace, names);
	}

	/**
	 * Creates a new {@link BlockHeap} from a block factory, shared properties and names.
	 * @param factory the block factory
	 * @param properties the block properties
	 * @param namespace the namespace
	 * @param names the names
	 * @return the block heap
	 * @param <T> the block class type
	 */
	static <T extends Block> BlockHeap register(BlockFactory<T> factory, Supplier<BlockBehaviour.Properties> properties, String namespace, List<String> names) {
		return new BlockHeapImpl(NameBlockFactory.of(factory), name -> name, name -> properties.get(), namespace, names);
	}

	/**
	 * Creates a new {@link BlockHeap} from a block factory, a name mapper, a properties mapper, and string constructors.
	 * @param factory the block factory
	 * @param nameMapper the name mapper
	 * @param propertiesMapper the properties mapper
	 * @param namespace the namespace
	 * @param constructors the string constructors
	 * @return the block heap
	 * @param <T> the block class type
	 */
	static <T extends Block> BlockHeap register(BlockFactory<T> factory, AutoMapper<String> nameMapper, Mapper<String, BlockBehaviour.Properties> propertiesMapper, String namespace, String... constructors)  {
		return BlockHeap.register(NameBlockFactory.of(factory), nameMapper, propertiesMapper, namespace, constructors);
	}

	/**
	 * Creates a new {@link BlockHeap} from a block factory, a name mapper, a properties mapper, and names.
	 * @param factory the block factory
	 * @param nameMapper the name mapper
	 * @param propertiesMapper the properties mapper
	 * @param namespace the namespace
	 * @param constructors the string constructors
	 * @return the block heap
	 * @param <T> the block class type
	 */
	static <T extends Block> BlockHeap register(BlockFactory<T> factory, AutoMapper<String> nameMapper, Mapper<String, BlockBehaviour.Properties> propertiesMapper, String namespace, List<String> constructors)  {
		return BlockHeap.register(NameBlockFactory.of(factory), nameMapper, propertiesMapper, namespace, constructors);
	}

	/**
	 * Creates a new {@link BlockHeap} from a name block factory, a name mapper, a properties mapper, and names.
	 * @param factory the name block factory
	 * @param nameMapper the name mapper
	 * @param propertiesMapper the properties mapper
	 * @param namespace the namespace
	 * @param constructors the string constructors
	 * @return the block heap
	 * @param <T> the block class type
	 */
	static <T extends Block> BlockHeap register(NameBlockFactory<T> factory, AutoMapper<String> nameMapper, Mapper<String, BlockBehaviour.Properties> propertiesMapper, String namespace, String... constructors) {
		return BlockHeap.register(factory, nameMapper, propertiesMapper, namespace, List.of(constructors));
	}

	/**
	 * Creates a new {@link BlockHeap} from a name block factory, a name mapper, a properties mapper, and names.
	 * @param factory the name block factory
	 * @param nameMapper the name mapper
	 * @param propertiesMapper the properties mapper
	 * @param namespace the namespace
	 * @param constructors the string constructors
	 * @return the block heap
	 * @param <T> the block class type
	 */
	static <T extends Block> BlockHeap register(NameBlockFactory<T> factory, AutoMapper<String> nameMapper, Mapper<String, BlockBehaviour.Properties> propertiesMapper, String namespace, List<String> constructors) {
		return new BlockHeapImpl(factory, nameMapper, propertiesMapper, namespace, constructors);
	}

	default BlockHeap registerBlockItems() {
		return this.registerBlockItems(new Item.Properties(), BlockItem::new);
	}

	default BlockHeap registerBlockItems(@NotNull Item.Properties properties) {
		return this.registerBlockItems(properties, BlockItem::new);
	}

	default BlockHeap registerBlockItems(@NotNull Item.Properties properties, @NotNull Function<Item, Item> tweaker) {
		return this.registerBlockItems(properties, BlockItem::new, tweaker);
	}

	default BlockHeap registerBlockItems(@NotNull Item.Properties properties, @NotNull BiFunction<Block, Item.Properties, Item> factory) {
		return this.registerBlockItems(properties, factory, item -> item);
	}

	BlockHeap registerBlockItems(@NotNull Item.Properties properties, @NotNull BiFunction<Block, Item.Properties, Item> factory, @NotNull Function<Item, Item> tweaker);

	List<Block> getEntries();

	BlockHeap map(AutoMapper<Block> mapper);

	void forEach(Consumer<Block> consumer);

	interface NameBlockFactory<T extends Block> {

		static <T extends Block> NameBlockFactory<T> of(BlockFactory<T> factory) {
			return (string, properties) -> factory.make(properties);
		}

		T make(String string, BlockBehaviour.Properties properties);
	}
}
