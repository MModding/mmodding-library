package com.mmodding.library.block.impl.wrapper;

import com.mmodding.library.block.api.BlockWithItem;
import com.mmodding.library.block.api.util.BlockFactory;
import com.mmodding.library.block.api.wrapper.BlockHeap;
import com.mmodding.library.datagen.api.management.resolver.DataContentResolver;
import com.mmodding.library.java.api.function.AutoMapper;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class BlockHeapImpl<T extends Block> implements BlockHeap<T> {

	private final Map<String, T> blocks;

	public BlockHeapImpl(BlockFactory<T> factory, FabricBlockSettings settings, List<String> names) {
		Map<String, T> blocks = new Object2ObjectOpenHashMap<>();
		names.forEach(name -> blocks.put(name, factory.make(settings)));
		this.blocks = blocks;
	}

	@Override
	public BlockHeap<T> withItem(FabricItemSettings settings) {
		this.getEntries().forEach(block -> block.withItem(settings));
		return this;
	}

	@Override
	public List<T> getEntries() {
		return List.copyOf(this.blocks.values());
	}

	public BlockHeapImpl<T> map(AutoMapper<T> mapper) {
		this.blocks.keySet().forEach(name -> this.blocks.computeIfPresent(name, (ignored, block) -> mapper.map(block)));
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
		for (Map.Entry<String, T> entry : this.blocks.entrySet()) {
			Identifier identifier = identifierMaker.apply(entry.getKey());
			Registry.register(Registries.BLOCK, identifier, entry.getValue());
			Item item = BlockWithItem.getItem(entry.getValue());
			if (item != null) {
				Registry.register(Registries.ITEM, identifier, item);
			}
		}
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void cutout() {
		this.getEntries().forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout()));
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void translucent() {
		this.getEntries().forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getTranslucent()));
	}

	static {
		// even if the BlockRelatives is using the interface type in a mod, at runtime its class is the implementation class
		DataContentResolver.<BlockHeap<Block>, Block>register(BlockHeapImpl.class, Block.class, BlockHeap::getEntries);
	}
}
