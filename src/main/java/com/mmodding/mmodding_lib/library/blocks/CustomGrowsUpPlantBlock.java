package com.mmodding.mmodding_lib.library.blocks;

import net.minecraft.block.AbstractPlantBlock;
import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.Direction;
import net.minecraft.util.random.RandomGenerator;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public class CustomGrowsUpPlantBlock {

	public static class Head extends AbstractPlantStemBlock implements BlockRegistrable {

		private final AtomicBoolean registered = new AtomicBoolean(false);

		private final int growLength;
		private final Block bodyBlock;
		private final Predicate<BlockState> chooseStemState;

		public Head(Settings settings, boolean tickWater, float growthChance, int growLength, Block bodyBlock, Predicate<BlockState> chooseStemState) {
			super(settings, Direction.UP, Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0), tickWater, growthChance);
			this.growLength = growLength;
			this.bodyBlock = bodyBlock;
			this.chooseStemState = chooseStemState;
		}

		@Override
		protected int getGrowthLength(RandomGenerator random) {
			return this.growLength;
		}

		@Override
		protected boolean chooseStemState(BlockState state) {
			return this.chooseStemState.test(state);
		}

		@Override
		protected Block getPlant() {
			return this.bodyBlock;
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

	public static class Body extends AbstractPlantBlock implements BlockRegistrable, BlockWithItem {

		private final AtomicBoolean registered = new AtomicBoolean(false);
		private BlockItem item = null;

		private final Head headBlock;

		public Body(Settings settings, boolean tickWater, Head headBlock) {
			this(settings, tickWater, headBlock, false);
		}

		public Body(Settings settings, boolean tickWater, Head headBlock, boolean hasItem) {
			this(settings, tickWater, headBlock, hasItem, (ItemGroup) null);
		}

		public Body(Settings settings, boolean tickWater, Head headBlock, boolean hasItem, ItemGroup itemGroup) {
			this(settings, tickWater, headBlock, hasItem, itemGroup != null ? new QuiltItemSettings().group(itemGroup) : new QuiltItemSettings());
		}

		public Body(Settings settings, boolean tickWater, Head headBlock, boolean hasItem, Item.Settings itemSettings) {
			super(settings, Direction.UP, Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0), tickWater);
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
}
