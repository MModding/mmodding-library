package com.mmodding.mmodding_lib.library.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class CustomBlockWithEntity extends BlockWithEntity implements BlockRegistrable, BlockWithItem {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	private BlockItem item = null;

	public CustomBlockWithEntity(Settings settings) {
		this(settings, false);
	}

	public CustomBlockWithEntity(Settings settings, boolean hasItem) {
		this(settings, hasItem, (ItemGroup) null);
	}

	public CustomBlockWithEntity(Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(settings, hasItem, itemGroup != null ? new FabricItemSettings().group(itemGroup) : new FabricItemSettings());
	}

	public CustomBlockWithEntity(Settings settings, boolean hasItem, Item.Settings itemSettings) {
		super(settings);
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
