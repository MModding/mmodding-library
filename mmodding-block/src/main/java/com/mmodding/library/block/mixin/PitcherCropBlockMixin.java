package com.mmodding.library.block.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mmodding.library.block.api.catalog.DoubleCropBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.PitcherCropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Function;

@Mixin(PitcherCropBlock.class)
public class PitcherCropBlockMixin extends DoublePlantBlockMixin {

	@WrapMethod(method = "makeShapes")
	public Function<BlockState, VoxelShape> cancelIfCustom(Operation<Function<BlockState, VoxelShape>> original) {
		return (((Object) this) instanceof DoubleCropBlock) ? _ -> Shapes.empty() : original.call();
	}

	@WrapMethod(method = "updateShape")
	public BlockState skipSuperCallIfCustom(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random, Operation<BlockState> original) {
		if (((Object) this) instanceof DoubleCropBlock) {
			return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
		}
		else {
			return original.call(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
		}
	}
}
