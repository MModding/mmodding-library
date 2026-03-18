package com.mmodding.library.block.api.wrapper;

import com.mmodding.library.block.api.util.BlockFactory;
import com.mmodding.library.block.impl.wrapper.BlockHeapImpl;
import com.mmodding.library.java.api.function.AutoMapper;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A simple class allowing to create and manipulate a bunch of blocks at the same time.
 */
public interface BlockHeap<T extends Block> {

	static <T extends Block> BlockHeap<T> create(BlockFactory<T> factory, FabricBlockSettings settings, String... names) {
		return new BlockHeapImpl<>(factory, settings, List.of(names));
	}

	static <T extends Block> BlockHeap<T> create(BlockFactory<T> factory, FabricBlockSettings settings, List<String> names) {
		return new BlockHeapImpl<>(factory, settings, names);
	}

	List<T> getEntries();

	BlockHeapImpl<T> map(AutoMapper<T> mapper);

	void forEach(Consumer<T> consumer);

	/**
	 * Registers every block inside the heap.
	 * @param identifierMaker the identifier maker turning the heap's block string names into usable identifiers for registration
	 */
	void register(Function<String, Identifier> identifierMaker);
}
