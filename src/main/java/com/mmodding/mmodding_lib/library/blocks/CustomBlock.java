package com.mmodding.mmodding_lib.library.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomBlock extends Block implements BlockRegistrable, BlockWithItem {
    private final AtomicBoolean registered = new AtomicBoolean(false);
    private BlockItem item = null;

    public CustomBlock(Settings settings) {
        this(settings, false);
    }

    public CustomBlock(Settings settings, boolean hasItem) {
        this(settings, hasItem, (ItemGroup) null);
    }

	public CustomBlock(Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(settings, hasItem, itemGroup != null ? new QuiltItemSettings().group(itemGroup) : new QuiltItemSettings());
	}

    public CustomBlock(Settings settings, boolean hasItem, Item.Settings itemSettings) {
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
