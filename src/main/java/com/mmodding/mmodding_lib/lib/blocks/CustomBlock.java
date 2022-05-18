package com.mmodding.mmodding_lib.lib.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
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
        this(settings, hasItem, null);
    }

    public CustomBlock(Settings settings, boolean hasItem, ItemGroup itemGroup) {
        super(settings);
        if (hasItem) {
            if (itemGroup != null) this.item = new BlockItem(this, new QuiltItemSettings().group(itemGroup));
            else this.item = new BlockItem(this, new QuiltItemSettings());
        }
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
