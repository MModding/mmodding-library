package com.mmodding.mmodding_lib.library.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.TorchBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.particle.ParticleEffect;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomTorchBlock extends TorchBlock implements BlockRegistrable, BlockWithItem {

    private final AtomicBoolean registered = new AtomicBoolean(false);

    private BlockItem item = null;

    public CustomTorchBlock(Settings settings, ParticleEffect particle) {
        this(settings, particle, false);
    }

    public CustomTorchBlock(Settings settings, ParticleEffect particle, boolean hasItem) {
        this(settings, particle, hasItem, (ItemGroup) null);
    }

	public CustomTorchBlock(Settings settings, ParticleEffect particle, boolean hasItem, ItemGroup itemGroup) {
		this(settings, particle, hasItem, itemGroup != null ? new FabricItemSettings().group(itemGroup) : new FabricItemSettings());
	}

    public CustomTorchBlock(Settings settings, ParticleEffect particle, boolean hasItem, Item.Settings itemSettings) {
        super(settings, particle);
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
