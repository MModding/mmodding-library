package com.mmodding.library.block.impl.wrapper;

import com.mmodding.library.block.api.wrapper.BlockHeap;
import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.java.api.function.Mapper;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class BlockHeapImpl implements BlockHeap {

	private final Map<String, Block> blocks;

	public BlockHeapImpl(BlockHeap.NameBlockFactory<? extends Block> factory, AutoMapper<String> nameMapper, Mapper<String, BlockBehaviour.Properties> propertiesMapper, String namespace, List<String> constructors) {
		Map<String, Block> blocks = new Object2ObjectLinkedOpenHashMap<>();
		for (String constructor : constructors) {
			String name = nameMapper.map(constructor);
			Block block = Blocks.register(
				ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(namespace, name)),
				properties -> factory.make(constructor, properties),
				propertiesMapper.map(constructor)
			);
			blocks.put(constructor, block);
		}
		this.blocks = blocks;
	}

	@Override
	public BlockHeap registerBlockItems(Item.@NotNull Properties properties, @NotNull BiFunction<Block, Item.Properties, Item> factory, @NotNull Function<Item, Item> tweaker) {
		this.getEntries().forEach(block -> block.registerItem(properties, factory, tweaker));
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
}
