package com.mmodding.library.block.impl.wrapper;

import com.mmodding.library.block.api.util.BlockFactory;
import com.mmodding.library.block.api.wrapper.BlockRelatives;
import com.mmodding.library.block.mixin.BlockFamilyAccessor;
import com.mmodding.library.core.api.registry.IdentifierUtil;
import com.mmodding.library.datagen.api.management.resolver.DataContentResolver;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlockRelativesImpl implements BlockRelatives {

	private final BlockSetType setType;
	private final Identifier identifier;
	private final Block mainBlock;
	private final TagKey<Block> blockTagKey;
	private final TagKey<Item> itemTagKey;
	private final Map<BlockFamily.Variant, Block> variants;

	public <T extends Block> BlockRelativesImpl(Identifier identifier, BlockSetType setType, Block.Properties sharedProperties, String mainSuffix, BlockFactory<T> mainFactory) {
		this.setType = setType;
		this.identifier = identifier;
		this.mainBlock = Blocks.register(ResourceKey.create(Registries.BLOCK, this.identifier.withPath(path -> path + mainSuffix)), mainFactory::make, sharedProperties);
		this.mainBlock.registerItem(new Item.Properties());
		this.blockTagKey = TagKey.create(Registries.BLOCK, identifier);
		this.itemTagKey = TagKey.create(Registries.ITEM, identifier);
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
	public <T extends Block> BlockRelatives register(BlockFamily.Variant variant, BlockFactory<T> factory) {
		ResourceKey<Block> variantKey = ResourceKey.create(Registries.BLOCK, IdentifierUtil.extend(this.identifier, variant.getRecipeGroup()));
		Block block = Blocks.register(variantKey, factory::make, Block.Properties.ofFullCopy(this.mainBlock));
		this.variants.put(variant, block);
		if (!variant.equals(BlockFamily.Variant.SIGN) && !variant.equals(BlockFamily.Variant.WALL_SIGN)) {
			block.registerItem();
		}
		else if (!this.variants.containsKey(BlockFamily.Variant.SIGN) || !this.variants.containsKey(BlockFamily.Variant.WALL_SIGN)) {
			Block signBlock;
			Block wallSignBlock;
			if (this.variants.containsKey(BlockFamily.Variant.SIGN)) {
				signBlock = this.variants.get(BlockFamily.Variant.SIGN);
				wallSignBlock = block;
			}
			else {
				signBlock = block;
				wallSignBlock = this.variants.get(BlockFamily.Variant.WALL_SIGN);
			}
			ResourceKey<Item> signKey = ResourceKey.create(Registries.ITEM, IdentifierUtil.extend(this.identifier, BlockFamily.Variant.SIGN.getRecipeGroup()));
			Items.registerItem(signKey, properties -> new SignItem(signBlock, wallSignBlock, properties));
		}
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
