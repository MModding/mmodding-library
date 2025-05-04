package com.mmodding.mmodding_lib.library.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public class CustomTallPlantBlock extends TallPlantBlock implements BlockRegistrable, BlockWithItem {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	private BlockItem item = null;

	private final Predicate<BlockState> placementConditions;

	public CustomTallPlantBlock(Settings settings) {
		this(settings, false);
	}

	public CustomTallPlantBlock(Settings settings, boolean hasItem) {
		this(settings, hasItem, (ItemGroup) null);
	}

	public CustomTallPlantBlock(Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(settings, hasItem, itemGroup != null ? new FabricItemSettings().group(itemGroup) : new FabricItemSettings());
	}

	public CustomTallPlantBlock(Settings settings, boolean hasItem, Item.Settings itemSettings) {
		this(floor -> floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND), settings, hasItem, itemSettings);
	}

	public CustomTallPlantBlock(Predicate<BlockState> placementConditions, Settings settings) {
		this(placementConditions, settings, false);
	}

	public CustomTallPlantBlock(Predicate<BlockState> placementConditions, Settings settings, boolean hasItem) {
		this(placementConditions, settings, hasItem, (ItemGroup) null);
	}

	public CustomTallPlantBlock(Predicate<BlockState> placementConditions, Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(placementConditions, settings, hasItem, itemGroup != null ? new FabricItemSettings().group(itemGroup) : new FabricItemSettings());
	}

	public CustomTallPlantBlock(Predicate<BlockState> placementConditions, Settings settings, boolean hasItem, Item.Settings itemSettings) {
		super(settings);
		if (hasItem) this.item = new BlockItem(this, itemSettings);
		this.placementConditions = placementConditions;
	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isSideSolidFullSquare(world, pos, Direction.UP) && this.placementConditions.test(floor);
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
