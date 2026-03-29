package com.mmodding.library.block.impl.wrapper;

import com.mmodding.library.block.api.util.BlockFactory;
import com.mmodding.library.block.api.wrapper.BlockHeap;
import com.mmodding.library.datagen.api.management.resolver.DataContentResolver;
import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.java.api.function.Mapper;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class BlockHeapImpl implements BlockHeap {

	private final Map<String, Block> blocks;

	public BlockHeapImpl(BlockHeap.NameBlockFactory<? extends Block> factory, AutoMapper<String> nameMapper, Mapper<String, FabricBlockSettings> settingsMapper, List<String> constructors) {
		Map<String, Block> blocks = new Object2ObjectLinkedOpenHashMap<>();
		constructors.forEach(constructor -> blocks.put(nameMapper.map(constructor), factory.make(constructor, settingsMapper.map(constructor))));
		this.blocks = blocks;
	}

	@Override
	public BlockHeap withItem(Item.@NotNull Settings settings, @NotNull BiFunction<Block, Item.Settings, Item> factory, @NotNull Function<Item, Item> tweaker) {
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
			Registry.register(Registries.BLOCK, identifier, entry.getValue());
			Item item = entry.getValue().asItem();
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
		DataContentResolver.register(BlockHeapImpl.class, Block.class, BlockHeap::getEntries);
	}
}
