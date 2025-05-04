package com.mmodding.mmodding_lib.library.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomCraftingTableBlock extends CraftingTableBlock implements BlockRegistrable, BlockWithItem {

    private final AtomicBoolean registered = new AtomicBoolean(false);

    private BlockItem item = null;

    public CustomCraftingTableBlock(Settings settings) {
        this(settings, false);
    }

    public CustomCraftingTableBlock(Settings settings, boolean hasItem) {
        this(settings, hasItem, (ItemGroup) null);
    }

	public CustomCraftingTableBlock(Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(settings, hasItem, itemGroup != null ? new FabricItemSettings().group(itemGroup) : new FabricItemSettings());
	}

    public CustomCraftingTableBlock(Settings settings, boolean hasItem, Item.Settings itemSettings) {
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
