package com.mmodding.mmodding_lib.library.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomPressurePlateBlock extends PressurePlateBlock implements BlockRegistrable, BlockWithItem {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	private BlockItem item = null;

	public CustomPressurePlateBlock(ActivationRule type, Settings settings) {
		this(type, settings, false);
	}

	public CustomPressurePlateBlock(ActivationRule type, Settings settings, boolean hasItem) {
		this(type, settings, hasItem, (ItemGroup) null);
	}

	public CustomPressurePlateBlock(ActivationRule type, Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(type, settings, hasItem, itemGroup != null ? new QuiltItemSettings().group(itemGroup) : new QuiltItemSettings());
	}

	public CustomPressurePlateBlock(ActivationRule type, Settings settings, boolean hasItem, Item.Settings itemSettings) {
		super(type, settings);
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
