package com.mmodding.library.block.api.util;

import com.mmodding.library.java.api.function.SingleTypeFunction;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A simple class allowing to create and manipulate a bunch of blocks at the same time.
 */
public class BlockHeap<T extends Block> {

	private final Map<String, T> blocks;

	private BlockHeap(BlockFactory<T> factory, FabricBlockSettings settings, List<String> names) {
		Map<String, T> blocks = new Object2ObjectOpenHashMap<>();
		names.forEach(name -> blocks.put(name, factory.make(settings)));
		this.blocks = blocks;
	}

	public static <T extends Block> BlockHeap<T> create(BlockFactory<T> factory, FabricBlockSettings settings, String... names) {
		return BlockHeap.create(factory, settings, List.of(names));
	}

	public static <T extends Block> BlockHeap<T> create(BlockFactory<T> factory, FabricBlockSettings settings, List<String> names) {
		return new BlockHeap<>(factory, settings, names);
	}

	public BlockHeap<T> map(SingleTypeFunction<T> mapper) {
		this.blocks.keySet().forEach(name -> this.blocks.computeIfPresent(name, (ignored, block) -> mapper.apply(block)));
		return this;
	}

	public void forEach(Consumer<T> consumer) {
		this.blocks.forEach((name, block) -> consumer.accept(block));
	}

	/**
	 * Registers every block inside the heap.
	 * @param identifierMaker the identifier maker turning the heap's block string names into usable identifiers for registration
	 */
	public void register(Function<String, Identifier> identifierMaker) {
		this.blocks.forEach((name, block) -> Registry.register(Registries.BLOCK, identifierMaker.apply(name), block));
	}

	@FunctionalInterface
	public interface BlockFactory<T extends Block> {

		T make(FabricBlockSettings settings);
	}
}
