package com.mmodding.library.block.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.library.block.api.catalog.SimpleBedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BedBlock.class)
public class BedBlockMixin {

	@WrapMethod(method = "getBedOrientation")
	private static Direction invertIfSimple(BlockGetter level, BlockPos pos, Operation<Direction> original) {
		Direction result = original.call(level, pos);
		return level.getBlockState(pos).getBlock() instanceof SimpleBedBlock ? result.getOpposite() : result;
	}

	@WrapOperation(method = "useWithoutItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;relative(Lnet/minecraft/core/Direction;)Lnet/minecraft/core/BlockPos;"))
	private static BlockPos invertIfSimpleInUse(BlockPos instance, Direction direction, Operation<BlockPos> original, @Local(argsOnly = true, name = "state") BlockState state) {
		return state.getBlock() instanceof SimpleBedBlock ? original.call(instance, direction.getOpposite()) : original.call(instance, direction);
	}

	@ModifyExpressionValue(method = "updateShape", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/BedBlock;getNeighbourDirection(Lnet/minecraft/world/level/block/state/properties/BedPart;Lnet/minecraft/core/Direction;)Lnet/minecraft/core/Direction;"))
	private Direction invertIfSimple(Direction original, BlockState state) {
		return state.getBlock() instanceof SimpleBedBlock ? original.getOpposite() : original;
	}

	@ModifyExpressionValue(method = "getStateForPlacement", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/context/BlockPlaceContext;getHorizontalDirection()Lnet/minecraft/core/Direction;"))
	private Direction invertIfSimple(Direction original, BlockPlaceContext context) {
		return ((BedBlock) (Object) this) instanceof SimpleBedBlock ? original.getOpposite() : original;
	}

	@ModifyExpressionValue(method = "playerWillDestroy", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/BedBlock;getNeighbourDirection(Lnet/minecraft/world/level/block/state/properties/BedPart;Lnet/minecraft/core/Direction;)Lnet/minecraft/core/Direction;"))
	private Direction invertIfSimple(Direction original, Level level, BlockPos pos, BlockState state) {
		return state.getBlock() instanceof SimpleBedBlock ? original.getOpposite() : original;
	}

	@WrapMethod(method = "getConnectedDirection")
	private static Direction invertIfSimple(BlockState state, Operation<Direction> original) {
		Direction result = original.call(state);
		return state.getBlock() instanceof SimpleBedBlock ? result.getOpposite() : result;
	}

	@ModifyExpressionValue(method = "findStandUpPosition", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Direction;getClockWise()Lnet/minecraft/core/Direction;"))
	private static Direction invertIfSimple(Direction original, EntityType<?> type, CollisionGetter level, BlockPos pos) {
		return level.getBlockState(pos).getBlock() instanceof SimpleBedBlock ? original.getOpposite() : original;
	}

	@WrapOperation(method = "setPlacedBy", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;relative(Lnet/minecraft/core/Direction;)Lnet/minecraft/core/BlockPos;"))
	private static BlockPos invertIfSimpleInPlaced(BlockPos instance, Direction direction, Operation<BlockPos> original, @Local(argsOnly = true, name = "state") BlockState state) {
		return state.getBlock() instanceof SimpleBedBlock ? original.call(instance, direction.getOpposite()) : original.call(instance, direction);
	}

	@WrapOperation(method = "getSeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;relative(Lnet/minecraft/core/Direction;I)Lnet/minecraft/core/BlockPos;"))
	private static BlockPos invertIfSimpleInSeed(BlockPos instance, Direction direction, int steps, Operation<BlockPos> original, @Local(argsOnly = true, name = "state") BlockState state) {
		return state.getBlock() instanceof SimpleBedBlock ? original.call(instance, direction.getOpposite(), steps) : original.call(instance, direction, steps);
	}
}
