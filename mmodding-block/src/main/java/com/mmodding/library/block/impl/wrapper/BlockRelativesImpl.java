package com.mmodding.library.block.impl.wrapper;

import com.mmodding.library.block.api.util.BlockFactory;
import com.mmodding.library.block.api.wrapper.BlockRelatives;
import com.mmodding.library.block.mixin.BlockFamilyAccessor;
import com.mmodding.library.core.api.registry.IdentifierUtil;
import com.mmodding.library.datagen.api.management.resolver.DataContentResolver;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Impl Client Layer Registrations
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
		this.variants.put(variant, factory.make(this.sharedSettings));
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
		this.variants.forEach((variant, block) -> Registry.register(Registries.BLOCK, IdentifierUtil.extend(name, variant.getName()), block));
	}

	private BlockFamily initDataFamily() {
		BlockFamily family = BlockFamilyAccessor.mmodding$init(this.mainBlock);
		((BlockFamilyAccessor) family).mmodding$getVariants().putAll(this.variants);
		return family;
	}

	static {
		DataContentResolver.register(BlockRelatives.class, BlockFamily.class, input -> List.of(((BlockRelativesImpl) input).initDataFamily()));
	}
}
