package com.mmodding.library.fluid.api;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class BehavedFluidBlock extends Block {

	private final Supplier<FluidBehavior> behavior;

	public BehavedFluidBlock(Supplier<FluidBehavior> behavior, Settings settings) {
		super(settings);
		this.behavior = behavior;
	}

	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}

	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		return Collections.emptyList();
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.empty();
	}

	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, RandomGenerator random) {
		this.behavior.get().randomDisplayTick(state, world, pos, random);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(this.behavior.get().getProperty());
	}

	public FluidBehavior getBehavior() {
		return this.behavior.get();
	}
}
