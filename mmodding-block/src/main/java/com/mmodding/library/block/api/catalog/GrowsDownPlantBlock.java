package com.mmodding.library.block.api.catalog;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.java.api.object.Wrapper;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import java.util.function.Predicate;

public class GrowsDownPlantBlock implements Wrapper<GrowsDownPlantBlock, Block> {

	private Head head;
	private Body body;

	public GrowsDownPlantBlock(BlockBehaviour.Properties settings, boolean tickWater, float growthChance, int growLength, Predicate<BlockState> chooseStemState) {
		this(Head::new, Body::new, settings, tickWater, growthChance, growLength, chooseStemState);
	}

	public GrowsDownPlantBlock(HeadMaker headMaker, BodyMaker bodyMaker, BlockBehaviour.Properties settings, boolean tickWater, float growthChance, int growLength, Predicate<BlockState> chooseStemState) {
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

	public GrowsDownPlantBlock configureHead(AutoMapper<Head> mapper) {
		this.head = mapper.map(this.head);
		return this;
	}

	public GrowsDownPlantBlock configureBody(AutoMapper<Body> mapper) {
		this.body = mapper.map(this.body);
		return this;
	}

	@Override
	public GrowsDownPlantBlock configure(AutoMapper<Block> mapper) {
		this.configureHead(head -> (Head) mapper.map(head));
		this.configureBody(body -> (Body) mapper.map(body));
		return this;
	}

	public void register(AdvancedContainer mod, String path) {
		mod.register(BuiltInRegistries.BLOCK, path + "_head", this.head);
		mod.register(BuiltInRegistries.BLOCK, path, this.body);
	}

	public static class Head extends GrowingPlantHeadBlock {

		private static final MapCodec<GrowingPlantHeadBlock> CODEC = simpleCodec(properties -> new Head(properties, null, false, 0.0f, 0, ignored -> true));

		private final GrowsDownPlantBlock plant;
		private final int growLength;
		private final Predicate<BlockState> chooseStemState;

		protected Head(BlockBehaviour.Properties properties, GrowsDownPlantBlock plant, boolean tickWater, float growthChance, int growLength, Predicate<BlockState> chooseStemState) {
			super(properties, Direction.DOWN, Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0), tickWater, growthChance);
			this.plant = plant;
			this.growLength = growLength;
			this.chooseStemState = chooseStemState;
		}

		@Override
		protected MapCodec<? extends GrowingPlantHeadBlock> codec() {
			return CODEC;
		}

		@Override
		protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
			return this.growLength;
		}

		@Override
		protected boolean canGrowInto(BlockState state) {
			return this.chooseStemState.test(state);
		}

		@Override
		protected Block getBodyBlock() {
			return this.plant.body;
		}
	}

	public static class Body extends GrowingPlantBodyBlock {

		private static final MapCodec<GrowingPlantBodyBlock> CODEC = simpleCodec(properties -> new Body(properties, null, false));

		private final GrowsDownPlantBlock plant;

		protected Body(Properties settings, GrowsDownPlantBlock plant, boolean tickWater) {
			super(settings, Direction.DOWN, Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0), tickWater);
			this.plant = plant;
		}

		@Override
		protected MapCodec<? extends GrowingPlantBodyBlock> codec() {
			return CODEC;
		}

		@Override
		protected GrowingPlantHeadBlock getHeadBlock() {
			return this.plant.head;
		}
	}

	public interface HeadMaker {

		Head make(BlockBehaviour.Properties settings, GrowsDownPlantBlock plant, boolean tickWater, float growthChance, int growLength, Predicate<BlockState> chooseStemState);
	}

	public interface BodyMaker {

		Body make(BlockBehaviour.Properties settings, GrowsDownPlantBlock plant, boolean tickWater);
	}

	/**
	 * Utility Interface helping about Growing Down Plants having Fruits.
	 */
	public interface FruitsHolder {

		BooleanProperty getFruitsProperty();

		Item getFruitItem(BlockState state, Level world, BlockPos pos);

		int getPickingCount(BlockState state, Level world, BlockPos pos);

		default InteractionResult pickBerries(BlockState state, Level world, BlockPos pos) {
			if (state.getValue(this.getFruitsProperty())) {
				Block.popResource(world, pos, new ItemStack(this.getFruitItem(state, world, pos), this.getPickingCount(state, world, pos)));
				world.playSound(
					null,
					pos,
					SoundEvents.CAVE_VINES_PICK_BERRIES,
					SoundSource.BLOCKS,
					1.0f,
					Mth.randomBetween(world.getRandom(), 0.8f, 1.2f)
				);
				world.setBlock(pos, state.setValue(this.getFruitsProperty(), Boolean.FALSE), Block.UPDATE_CLIENTS);
				return InteractionResult.SUCCESS;
			} else {
				return InteractionResult.PASS;
			}
		}

		default boolean hasBerries(BlockState state) {
			return state.hasProperty(this.getFruitsProperty()) && state.getValue(this.getFruitsProperty());
		}
	}
}
