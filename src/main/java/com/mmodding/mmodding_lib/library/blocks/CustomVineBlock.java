package com.mmodding.mmodding_lib.library.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.VineBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomVineBlock extends VineBlock implements BlockRegistrable, BlockWithItem {

    private final AtomicBoolean registered = new AtomicBoolean(false);

    private BlockItem item = null;

    public CustomVineBlock(Settings settings) {
        this(settings, false);
    }

    public CustomVineBlock(Settings settings, boolean hasItem) {
        this(settings, hasItem, (ItemGroup) null);
    }

	public CustomVineBlock(Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(settings, hasItem, itemGroup != null ? new FabricItemSettings().group(itemGroup) : new FabricItemSettings());
	}

    public CustomVineBlock(Settings settings, boolean hasItem, Item.Settings itemSettings) {
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
