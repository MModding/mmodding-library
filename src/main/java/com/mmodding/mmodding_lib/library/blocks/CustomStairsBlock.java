package com.mmodding.mmodding_lib.library.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomStairsBlock extends StairsBlock implements BlockRegistrable, BlockWithItem {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	private BlockItem item = null;

	public CustomStairsBlock(BlockState blockState, Settings settings) {
		this(blockState, settings, false);
	}

	public CustomStairsBlock(BlockState blockState, Settings settings, boolean hasItem) {
		this(blockState, settings, hasItem, (ItemGroup) null);
	}

	public CustomStairsBlock(BlockState blockState, Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(blockState, settings, hasItem, itemGroup != null ? new FabricItemSettings().group(itemGroup) : new FabricItemSettings());
	}

	public CustomStairsBlock(BlockState blockState, Settings settings, boolean hasItem, Item.Settings itemSettings) {
		super(blockState, settings);
		if (hasItem) this.item = new BlockItem(this, itemSettings);
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
