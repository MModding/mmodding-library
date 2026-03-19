package com.mmodding.library.block.impl.wrapper;

import com.mmodding.library.block.api.BlockWithItem;
import com.mmodding.library.block.api.util.BlockFactory;
import com.mmodding.library.block.api.wrapper.BlockRelatives;
import com.mmodding.library.block.mixin.BlockFamilyAccessor;
import com.mmodding.library.core.api.registry.IdentifierUtil;
import com.mmodding.library.datagen.api.management.resolver.DataContentResolver;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlockRelativesImpl implements BlockRelatives {

	private final BlockSetType setType;
	private final FabricBlockSettings sharedSettings;
	private final String mainName;
	private final Block mainBlock;
	private final Map<BlockFamily.Variant, Block> variants;

	public <T extends Block> BlockRelativesImpl(BlockSetType setType, FabricBlockSettings sharedSettings, String mainName, BlockFactory<T> mainFactory) {
		this.setType = setType;
		this.sharedSettings = sharedSettings;
		this.mainName = mainName;
		this.mainBlock = mainFactory.make(sharedSettings);
		this.variants = new Object2ObjectOpenHashMap<>();
	}

	@Override
	public BlockSetType getSetType() {
		return this.setType;
	}

	@Override
	public Block getMain() {
		return this.mainBlock;
	}

	@Override
	public Block get(BlockFamily.Variant name) {
		return this.variants.get(name);
	}

	@Override
	public <T extends Block> BlockRelatives push(BlockFamily.Variant variant, BlockFactory<T> factory) {
		T block = factory.make(this.sharedSettings);
		block.withItem(new FabricItemSettings());
		this.variants.put(variant, block);
		return this;
	}

	@Override
	public List<Block> getEntries() {
		List<Block> entries = new ArrayList<>();
		entries.add(this.mainBlock);
		entries.addAll(this.variants.values());
		return List.copyOf(entries);
	}

	@Override
	public void register(Identifier name) {
		Registry.register(Registries.BLOCK, IdentifierUtil.extend(name, this.mainName), this.mainBlock);
		for (Map.Entry<BlockFamily.Variant, Block> entry : this.variants.entrySet()) {
			Identifier identifier = IdentifierUtil.extend(name, entry.getKey().getName());
			Registry.register(Registries.BLOCK, identifier, entry.getValue());
			Registry.register(Registries.ITEM, identifier, BlockWithItem.getItem(entry.getValue()));
		}
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void cutoutMain() {
		BlockRenderLayerMap.INSTANCE.putBlock(this.mainBlock, RenderLayer.getCutout());
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void translucentMain() {
		BlockRenderLayerMap.INSTANCE.putBlock(this.mainBlock, RenderLayer.getTranslucent());
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void cutoutVariant(BlockFamily.Variant variant) {
		if (this.variants.containsKey(variant)) {
			BlockRenderLayerMap.INSTANCE.putBlock(this.variants.get(variant), RenderLayer.getCutout());
		}
		else {
			throw new IllegalStateException("This variant is not part of this block relatives");
		}
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void translucentVariant(BlockFamily.Variant variant) {
		if (this.variants.containsKey(variant)) {
			BlockRenderLayerMap.INSTANCE.putBlock(this.variants.get(variant), RenderLayer.getTranslucent());
		}
		else {
			throw new IllegalStateException("This variant is not part of this block relatives");
		}
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void cutoutAll() {
		this.cutoutMain();
		this.variants.keySet().forEach(this::cutoutVariant);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void translucentAll() {
		this.translucentMain();
		this.variants.keySet().forEach(this::translucentVariant);
	}

	private BlockFamily initDataFamily() {
		BlockFamily family = BlockFamilyAccessor.mmodding$init(this.mainBlock);
		((BlockFamilyAccessor) family).mmodding$getVariants().putAll(this.variants);
		return family;
	}

	static {
		// even if the BlockRelatives is using the interface type in a mod, at runtime its class is the implementation class
		DataContentResolver.register(BlockRelativesImpl.class, BlockFamily.class, input -> List.of(((BlockRelativesImpl) input).initDataFamily()));
	}
}
