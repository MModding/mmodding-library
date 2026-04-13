package com.mmodding.library.block.impl.wrapper;

import com.mmodding.library.block.api.wrapper.BlockHeap;
import com.mmodding.library.datagen.api.management.resolver.DataContentResolver;
import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.java.api.function.Mapper;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class BlockHeapImpl implements BlockHeap {

	private final Map<String, Block> blocks;

	public BlockHeapImpl(BlockHeap.NameBlockFactory<? extends Block> factory, AutoMapper<String> nameMapper, Mapper<String, BlockBehaviour.Properties> propertiesMapper, List<String> constructors) {
		Map<String, Block> blocks = new Object2ObjectLinkedOpenHashMap<>();
		constructors.forEach(constructor -> blocks.put(nameMapper.map(constructor), factory.make(constructor, propertiesMapper.map(constructor))));
		this.blocks = blocks;
	}

	@Override
	public BlockHeap withItem(Item.@NotNull Properties settings, @NotNull BiFunction<Block, Item.Properties, Item> factory, @NotNull Function<Item, Item> tweaker) {
		this.getEntries().forEach(block -> block.withItem(settings, factory, tweaker));
		return this;
	}

	@Override
	public List<Block> getEntries() {
		return List.copyOf(this.blocks.values());
	}

	public BlockHeap map(AutoMapper<Block> mapper) {
		this.blocks.keySet().forEach(name -> this.blocks.computeIfPresent(name, (ignored, block) -> mapper.map(block)));
		return this;
	}

	public void forEach(Consumer<Block> consumer) {
		this.blocks.forEach((name, block) -> consumer.accept(block));
	}

	/**
	 * Registers every block inside the heap.
	 * @param identifierMaker the identifier maker turning the heap's block string names into usable identifiers for registration
	 */
	public void register(Function<String, Identifier> identifierMaker) {
		for (Map.Entry<String, Block> entry : this.blocks.entrySet()) {
			Identifier identifier = identifierMaker.apply(entry.getKey());
			Registry.register(BuiltInRegistries.BLOCK, identifier, entry.getValue());
			Item item = entry.getValue().asItem();
			if (!item.equals(Items.AIR)) {
				Registry.register(BuiltInRegistries.ITEM, identifier, item);
			}
		}
	}

	static {
		// even if the BlockRelatives is using the interface type in a mod, at runtime its class is the implementation class
		DataContentResolver.register(BlockHeapImpl.class, Block.class, BlockHeap::getEntries);
	}
}
