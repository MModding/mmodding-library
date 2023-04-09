package com.mmodding.mmodding_lib.library.blocks;

import net.minecraft.block.FenceBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomFenceBlock extends FenceBlock implements BlockRegistrable, BlockWithItem {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	private BlockItem item = null;

	public CustomFenceBlock(Settings settings) {
		this(settings, false);
	}

	public CustomFenceBlock(Settings settings, boolean hasItem) {
		this(settings, hasItem, (ItemGroup) null);
	}

	public CustomFenceBlock(Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(settings, hasItem, itemGroup != null ? new QuiltItemSettings().group(itemGroup) : new QuiltItemSettings());
	}

	public CustomFenceBlock(Settings settings, boolean hasItem, Item.Settings itemSettings) {
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
