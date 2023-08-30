package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.mmodding_lib.ducks.FlowableFluidDuckInterface;
import com.mmodding.mmodding_lib.library.fluids.FluidExtensions;
import com.mmodding.mmodding_lib.library.fluids.collisions.FluidCollisionHandler;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidBlock.class)
public class FluidBlockMixin {

	@Shadow
	@Final
	protected FlowableFluid fluid;

	@WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FlowableFluid;getStill(Z)Lnet/minecraft/fluid/FluidState;", ordinal = 0))
	private FluidState initFirst(FlowableFluid fluid, boolean falling, Operation<FluidState> original) {
		if (this.getDuckedFlowableFluid(fluid).mmodding_lib$hasStillState()) {
			return this.getDuckedFlowableFluid(fluid).mmodding_lib$getStillState();
		}
		else {
			return original.call(fluid, falling);
		}
	}

	@WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FlowableFluid;getFlowing(IZ)Lnet/minecraft/fluid/FluidState;", ordinal = 0))
	private FluidState initSecond(FlowableFluid fluid, int level, boolean falling, Operation<FluidState> original, @Local int i) {
		if (this.getDuckedFlowableFluid(fluid).mmodding_lib$hasFlowingStates()) {
			return this.getDuckedFlowableFluid(fluid).mmodding_lib$getFlowingStates().apply(i);
		}
		else {
			return original.call(fluid, level, falling);
		}
	}

	@WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FlowableFluid;getFlowing(IZ)Lnet/minecraft/fluid/FluidState;", ordinal = 1))
	private FluidState initThird(FlowableFluid fluid, int level, boolean falling, Operation<FluidState> original) {
		if (this.getDuckedFlowableFluid(fluid).mmodding_lib$hasStillState()) {
			return this.getDuckedFlowableFluid(fluid).mmodding_lib$getFlowingStates().apply(8);
		}
		else {
			return original.call(fluid, level, falling);
		}
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void last(FlowableFluid fluid, AbstractBlock.Settings settings, CallbackInfo ci) {
		this.getDuckedFlowableFluid(fluid).mmodding_lib$removeCustomStates();
	}

	@Inject(method = "receiveNeighborFluids", at = @At("HEAD"), cancellable = true)
	private void receiveNeighborFluids(World world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (this.fluid instanceof FluidExtensions extensions) {
			FluidCollisionHandler handler = extensions.getCollisionHandler();
            for (Direction direction : Direction.values()) {
				BlockPos blockPos = pos.offset(direction.getOpposite());
				BlockState blockState = handler.getCollisionResult(world, pos, state, blockPos, world.getBlockState(blockPos));
				if (blockState.isAir()) {
					BlockState fluidState = handler.getCollisionResult(world, pos, state, blockPos, world.getFluidState(blockPos));
					if (!fluidState.isAir()) {
						world.setBlockState(pos, fluidState);
						handler.afterCollision(world, pos, state, blockPos, world.getFluidState(blockPos));
						cir.setReturnValue(false);
					}
				}
				else {
					world.setBlockState(pos, blockState);
					handler.afterCollision(world, pos, state, blockPos, world.getBlockState(blockPos));
					cir.setReturnValue(false);
				}
			}
		}
	}

	@Unique
	private FlowableFluidDuckInterface getDuckedFlowableFluid(FlowableFluid fluid) {
		return (FlowableFluidDuckInterface) fluid;
	}
}
