package com.mmodding.library.fluid.mixin;

import com.mmodding.library.fluid.api.AdvancedFlowableFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LiquidBlock.class)
public class FluidBlockMixin {

	@Shadow
	@Final
	protected FlowingFluid fluid;

	@Inject(method = "shouldSpreadLiquid", at = @At("HEAD"))
	private void injectedCollisions(Level world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (this.fluid instanceof AdvancedFlowableFluid advanced) {
			for (Direction direction : LiquidBlock.POSSIBLE_FLOW_DIRECTIONS) {
				advanced.neighborCollision(world, pos, direction, pos.relative(direction));
			}
		}
	}
}
