package com.mmodding.mmodding_lib.library.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public class CustomFlowerBlock extends FlowerBlock implements BlockRegistrable, BlockWithItem {

	private final Predicate<BlockState> placementConditions;

	private final AtomicBoolean registered = new AtomicBoolean(false);

	private BlockItem item = null;

	public CustomFlowerBlock(StatusEffect suspiciousStewEffect, int effectDuration, Settings settings) {
		this(suspiciousStewEffect, effectDuration, settings, false);
	}

	public CustomFlowerBlock(StatusEffect suspiciousStewEffect, int effectDuration, Settings settings, boolean hasItem) {
		this(suspiciousStewEffect, effectDuration, settings, hasItem, (ItemGroup) null);
	}

	public CustomFlowerBlock(StatusEffect suspiciousStewEffect, int effectDuration, Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(suspiciousStewEffect, effectDuration, settings, hasItem, itemGroup != null ? new QuiltItemSettings().group(itemGroup) : new QuiltItemSettings());
	}

	public CustomFlowerBlock(StatusEffect suspiciousStewEffect, int effectDuration, Settings settings, boolean hasItem, Item.Settings itemSettings) {
		this(floor -> floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND), suspiciousStewEffect, effectDuration, settings, hasItem, itemSettings);
	}

	public CustomFlowerBlock(Predicate<BlockState> placementConditions, StatusEffect suspiciousStewEffect, int effectDuration, Settings settings) {
		this(placementConditions, suspiciousStewEffect, effectDuration, settings, false);
	}

	public CustomFlowerBlock(Predicate<BlockState> placementConditions, StatusEffect suspiciousStewEffect, int effectDuration, Settings settings, boolean hasItem) {
		this(placementConditions, suspiciousStewEffect, effectDuration, settings, hasItem, (ItemGroup) null);
	}

	public CustomFlowerBlock(Predicate<BlockState> placementConditions, StatusEffect suspiciousStewEffect, int effectDuration, Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(placementConditions, suspiciousStewEffect, effectDuration, settings, hasItem, itemGroup != null ? new QuiltItemSettings().group(itemGroup) : new QuiltItemSettings());
	}

	public CustomFlowerBlock(Predicate<BlockState> placementConditions, StatusEffect suspiciousStewEffect, int effectDuration, Settings settings, boolean hasItem, Item.Settings itemSettings) {
		super(suspiciousStewEffect, effectDuration, settings);
		if (hasItem) this.item = new BlockItem(this, itemSettings);
		this.placementConditions = placementConditions;
	}

	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return this.placementConditions.test(floor);
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
