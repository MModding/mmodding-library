package com.mmodding.mmodding_lib.library.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public class CustomPlantBlock extends PlantBlock implements BlockRegistrable, BlockWithItem {

	private final AtomicBoolean registered = new AtomicBoolean(false);
	private BlockItem item = null;

	private final Predicate<BlockState> placementConditions;

	public CustomPlantBlock(Settings settings) {
		this(settings, false);
	}

	public CustomPlantBlock(Settings settings, boolean hasItem) {
		this(settings, hasItem, (ItemGroup) null);
	}

	public CustomPlantBlock(Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(settings, hasItem, itemGroup != null ? new QuiltItemSettings().group(itemGroup) : new QuiltItemSettings());
	}

	public CustomPlantBlock(Settings settings, boolean hasItem, Item.Settings itemSettings) {
		this(floor -> floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND), settings, hasItem, itemSettings);
	}

	public CustomPlantBlock(Predicate<BlockState> placementConditions, Settings settings) {
		this(placementConditions, settings, false);
	}

	public CustomPlantBlock(Predicate<BlockState> placementConditions, Settings settings, boolean hasItem) {
		this(placementConditions, settings, hasItem, (ItemGroup) null);
	}

	public CustomPlantBlock(Predicate<BlockState> placementConditions, Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(placementConditions, settings, hasItem, itemGroup != null ? new QuiltItemSettings().group(itemGroup) : new QuiltItemSettings());
	}

	public CustomPlantBlock(Predicate<BlockState> placementConditions, Settings settings, boolean hasItem, Item.Settings itemSettings) {
		super(settings);
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
