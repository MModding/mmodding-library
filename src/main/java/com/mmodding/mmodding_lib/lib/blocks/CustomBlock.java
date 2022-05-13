package com.mmodding.mmodding_lib.lib.blocks;

import com.mmodding.mmodding_lib.lib.utils.RenderLayerUtils;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomBlock extends Block implements BlockRegistrable {
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

    public BlockItem getItem() {
        return this.item;
    }

    // Client
    public void cutout() {
        RenderLayerUtils.setCutout(this);
    }

    // Client
    public void translucent() {
        RenderLayerUtils.setTranslucent(this);
    }

    @Override
    public boolean isNotRegistered() {
        return !registered.get();
    }

    @Override
    public void setRegistered() {
        registered.set(true);
    }
}
