package com.mmodding.mmodding_lib.library.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.block.Material;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.WaterPlaceableBlockItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiPredicate;

public class CustomLilyPadBlock extends LilyPadBlock implements BlockRegistrable, BlockWithItem {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	private BlockItem item = null;

	private final BiPredicate<FluidState, BlockState> placementConditions;

	public CustomLilyPadBlock(Settings settings) {
		this(settings, false);
	}

	public CustomLilyPadBlock(Settings settings, boolean hasItem) {
		this(settings, hasItem, (ItemGroup) null);
	}

	public CustomLilyPadBlock(Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(settings, hasItem, itemGroup != null ? new FabricItemSettings().group(itemGroup) : new FabricItemSettings());
	}

	public CustomLilyPadBlock(Settings settings, boolean hasItem, Item.Settings itemSettings) {
		this((fluid, floor) -> fluid.isOf(Fluids.WATER) || floor.getMaterial().equals(Material.ICE), settings, hasItem, itemSettings);
	}

	public CustomLilyPadBlock(BiPredicate<FluidState, BlockState> placementConditions, Settings settings) {
		this(placementConditions, settings, false);
	}

	public CustomLilyPadBlock(BiPredicate<FluidState, BlockState> placementConditions, Settings settings, boolean hasItem) {
		this(placementConditions, settings, hasItem, (ItemGroup) null);
	}

	public CustomLilyPadBlock(BiPredicate<FluidState, BlockState> placementConditions, Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(placementConditions, settings, hasItem, itemGroup != null ? new FabricItemSettings().group(itemGroup) : new FabricItemSettings());
	}

	public CustomLilyPadBlock(BiPredicate<FluidState, BlockState> placementConditions, Settings settings, boolean hasItem, Item.Settings itemSettings) {
		super(settings);
		if (hasItem) this.item = new WaterPlaceableBlockItem(this, itemSettings);
		this.placementConditions = placementConditions;
	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return this.placementConditions.test(world.getFluidState(pos), floor) && world.getFluidState(pos.up()).isEmpty();
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
