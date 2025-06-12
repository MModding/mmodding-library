package com.mmodding.library.block.api.catalog;

import com.mmodding.library.core.api.registry.IdentifierUtil;
import com.mmodding.library.core.api.registry.Registrable;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class GrowsDownPlantBlock implements Registrable<Block> {

	private final Head head;
	private final Body body;

	public GrowsDownPlantBlock(AbstractBlock.Settings settings, boolean tickWater, float growthChance, int growLength, Predicate<BlockState> chooseStemState) {
		this(Head::new, Body::new, settings, tickWater, growthChance, growLength, chooseStemState);
	}

	public GrowsDownPlantBlock(HeadMaker headMaker, BodyMaker bodyMaker, AbstractBlock.Settings settings, boolean tickWater, float growthChance, int growLength, Predicate<BlockState> chooseStemState) {
		this.head = headMaker.make(settings, this, tickWater, growthChance, growLength, chooseStemState);
		this.body = bodyMaker.make(settings, this, tickWater);
	}

	public Head getHead() {
		return this.head;
	}

	public Body getBody() {
		return this.body;
	}

	// Returns the same block by default. If you have a fruit with that plant, you should add it in the state's properties
	public BlockState withFruits(BlockState state) {
		return state;
	}

	public void register(Identifier identifier) {
		this.head.register(Registries.BLOCK, IdentifierUtil.extend(identifier, "head"));
		this.body.register(Registries.BLOCK, identifier);
	}

	/* @ClientOnly
	public void cutout() {
		RenderLayerOperations.setCutout(this.head);
		RenderLayerOperations.setCutout(this.body);
	}

	@ClientOnly
	public void translucent() {
		RenderLayerOperations.setTranslucent(this.head);
		RenderLayerOperations.setTranslucent(this.body);
	}

	@ClientOnly
	public void transparent() {
		RenderLayerOperations.setTransparent(this.head);
		RenderLayerOperations.setTransparent(this.body);
	} */

	public static class Head extends AbstractPlantStemBlock {

		private final GrowsDownPlantBlock plant;
		private final int growLength;
		private final Predicate<BlockState> chooseStemState;

		protected Head(AbstractBlock.Settings settings, GrowsDownPlantBlock plant, boolean tickWater, float growthChance, int growLength, Predicate<BlockState> chooseStemState) {
			super(settings, Direction.DOWN, Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0), tickWater, growthChance);
			this.plant = plant;
			this.growLength = growLength;
			this.chooseStemState = chooseStemState;
		}

		@Override
		protected int getGrowthLength(Random random) {
			return this.growLength;
		}

		@Override
		protected boolean chooseStemState(BlockState state) {
			return this.chooseStemState.test(state);
		}

		@Override
		protected Block getPlant() {
			return this.plant.body;
		}
	}

	public static class Body extends AbstractPlantBlock {

		private final GrowsDownPlantBlock plant;

		protected Body(Settings settings, GrowsDownPlantBlock plant, boolean tickWater) {
			super(settings, Direction.DOWN, Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0), tickWater);
			this.plant = plant;
		}

		@Override
		protected AbstractPlantStemBlock getStem() {
			return this.plant.head;
		}
	}

	public interface HeadMaker {

		Head make(AbstractBlock.Settings settings, GrowsDownPlantBlock plant, boolean tickWater, float growthChance, int growLength, Predicate<BlockState> chooseStemState);
	}

	public interface BodyMaker {

		Body make(AbstractBlock.Settings settings, GrowsDownPlantBlock plant, boolean tickWater);
	}

	/**
	 * Utility Interface helping about Growing Down Plants having Fruits.
	 */
	public interface WithFruits {

		BooleanProperty getFruitsProperty();

		Item getFruitItem(BlockState state, World world, BlockPos pos);

		int getPickingCount(BlockState state, World world, BlockPos pos);

		default ActionResult pickBerries(BlockState state, World world, BlockPos pos) {
			if (state.get(this.getFruitsProperty())) {
				Block.dropStack(world, pos, new ItemStack(this.getFruitItem(state, world, pos), this.getPickingCount(state, world, pos)));
				world.playSound(
					null,
					pos,
					SoundEvents.BLOCK_CAVE_VINES_PICK_BERRIES,
					SoundCategory.BLOCKS,
					1.0f,
					MathHelper.nextBetween(world.getRandom(), 0.8f, 1.2f)
				);
				world.setBlockState(pos, state.with(this.getFruitsProperty(), Boolean.FALSE), Block.NOTIFY_LISTENERS);
				return ActionResult.success(world.isClient());
			} else {
				return ActionResult.PASS;
			}
		}

		default boolean hasBerries(BlockState state) {
			return state.contains(this.getFruitsProperty()) && state.get(this.getFruitsProperty());
		}
	}
}
