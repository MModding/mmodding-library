package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Blocks.class)
public interface BlocksAccessor {

	@Invoker("always")
	static boolean invokeAlways(BlockState state, BlockView world, BlockPos pos) {
		throw new AssertionError();
	}

	@Invoker("never")
	static boolean invokeNever(BlockState state, BlockView world, BlockPos pos) {
		throw new AssertionError();
	}
}
