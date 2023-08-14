package com.mmodding.mmodding_lib.library.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public class CustomSaplingBlock extends SaplingBlock implements BlockRegistrable, BlockWithItem {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	private BlockItem item = null;

	private final Predicate<BlockState> placementConditions;

	public CustomSaplingBlock(SaplingGenerator generator, Settings settings) {
		this(generator, settings, false);
	}

	public CustomSaplingBlock(SaplingGenerator generator, Settings settings, boolean hasItem) {
		this(generator, settings, hasItem, (ItemGroup) null);
	}

	public CustomSaplingBlock(SaplingGenerator generator, Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(generator, settings, hasItem, itemGroup != null ? new QuiltItemSettings().group(itemGroup) : new QuiltItemSettings());
	}

	public CustomSaplingBlock(SaplingGenerator generator, Settings settings, boolean hasItem, Item.Settings itemSettings) {
		this(generator, floor -> floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND), settings, hasItem, itemSettings);
	}

	public CustomSaplingBlock(SaplingGenerator generator, Predicate<BlockState> placementConditions, Settings settings) {
		this(generator, placementConditions, settings, false);
	}

	public CustomSaplingBlock(SaplingGenerator generator, Predicate<BlockState> placementConditions, Settings settings, boolean hasItem) {
		this(generator, placementConditions, settings, hasItem, (ItemGroup) null);
	}

	public CustomSaplingBlock(SaplingGenerator generator, Predicate<BlockState> placementConditions, Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(generator, placementConditions, settings, hasItem, itemGroup != null ? new QuiltItemSettings().group(itemGroup) : new QuiltItemSettings());
	}

	public CustomSaplingBlock(SaplingGenerator generator, Predicate<BlockState> placementConditions, Settings settings, boolean hasItem, Item.Settings itemSettings) {
		super(generator, settings);
		if (hasItem) this.item = new BlockItem(this, itemSettings);
		this.placementConditions = placementConditions;
	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return this.placementConditions.test(floor);
	}

	@Override
	public BlockItem getItem() {
		return this.item;
	}

	@Override
	public boolean isNotRegistered() {
		return !this.registered.get();
	}

	@Override
	public void setRegistered() {
		this.registered.set(true);
	}
}
