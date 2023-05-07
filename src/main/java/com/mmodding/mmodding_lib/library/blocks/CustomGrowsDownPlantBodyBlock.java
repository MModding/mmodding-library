package com.mmodding.mmodding_lib.library.blocks;

import net.minecraft.block.AbstractPlantBlock;
import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.Direction;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomGrowsDownPlantBodyBlock extends AbstractPlantBlock implements BlockRegistrable, BlockWithItem {

    private final AtomicBoolean registered = new AtomicBoolean(false);
    private BlockItem item = null;

    private final CustomGrowsDownPlantHeadBlock headBlock;

    public CustomGrowsDownPlantBodyBlock(Settings settings, boolean tickWater, CustomGrowsDownPlantHeadBlock headBlock) {
        this(settings, tickWater, headBlock, false);
    }

    public CustomGrowsDownPlantBodyBlock(Settings settings, boolean tickWater, CustomGrowsDownPlantHeadBlock headBlock, boolean hasItem) {
        this(settings, tickWater, headBlock, hasItem, (ItemGroup) null);
    }

	public CustomGrowsDownPlantBodyBlock(Settings settings, boolean tickWater, CustomGrowsDownPlantHeadBlock headBlock, boolean hasItem, ItemGroup itemGroup) {
		this(settings, tickWater, headBlock, hasItem, itemGroup != null ? new QuiltItemSettings().group(itemGroup) : new QuiltItemSettings());
	}

    public CustomGrowsDownPlantBodyBlock(Settings settings, boolean tickWater, CustomGrowsDownPlantHeadBlock headBlock, boolean hasItem, Item.Settings itemSettings) {
        super(settings, Direction.DOWN, Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0), tickWater);
        if (hasItem) this.item = new BlockItem(this, itemSettings);
        this.headBlock = headBlock;
    }

    @Override
    protected AbstractPlantStemBlock getStem() {
        return this.headBlock;
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
