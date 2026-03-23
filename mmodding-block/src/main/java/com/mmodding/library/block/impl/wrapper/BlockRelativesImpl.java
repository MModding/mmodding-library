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
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlockRelativesImpl implements BlockRelatives {

	private final BlockSetType setType;
	private final FabricBlockSettings sharedSettings;
	private final Identifier identifier;
	private final String mainSuffix;
	private final Block mainBlock;
	private final TagKey<Block> blockTagKey;
	private final TagKey<Item> itemTagKey;
	private final Map<BlockFamily.Variant, Block> variants;

	public <T extends Block> BlockRelativesImpl(Identifier identifier, BlockSetType setType, FabricBlockSettings sharedSettings, String mainSuffix, BlockFactory<T> mainFactory) {
		this.setType = setType;
		this.sharedSettings = sharedSettings;
		this.identifier = identifier;
		this.mainSuffix = mainSuffix;
		this.mainBlock = mainFactory.make(sharedSettings);
		this.mainBlock.withItem(new FabricItemSettings());
		this.blockTagKey = TagKey.of(RegistryKeys.BLOCK, identifier);
		this.itemTagKey = TagKey.of(RegistryKeys.ITEM, identifier);
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
	public TagKey<Block> getBlockTagKey() {
		return this.blockTagKey;
	}

	@Override
	public TagKey<Item> getItemTagKey() {
		return this.itemTagKey;
	}

	@Override
	public List<Block> getEntries() {
		List<Block> entries = new ArrayList<>();
		entries.add(this.mainBlock);
		entries.addAll(this.variants.values());
		return List.copyOf(entries);
	}

	@Override
	public void register() {
		Identifier mainIdentifier = this.identifier.withPath(path -> path + this.mainSuffix);
		Registry.register(Registries.BLOCK, mainIdentifier, this.mainBlock);
		Registry.register(Registries.ITEM, mainIdentifier, BlockWithItem.getItem(this.mainBlock));
		for (Map.Entry<BlockFamily.Variant, Block> entry : this.variants.entrySet()) {
			Identifier variantIdentifier = IdentifierUtil.extend(this.identifier, entry.getKey().getName());
			Registry.register(Registries.BLOCK, variantIdentifier, entry.getValue());
			Registry.register(Registries.ITEM, variantIdentifier, BlockWithItem.getItem(entry.getValue()));
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
