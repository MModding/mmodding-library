package com.mmodding.library.fluid.mixin;

import com.mmodding.library.fluid.api.AdvancedFlowableFluid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidBlock.class)
public class FluidBlockMixin {

	@Shadow
	@Final
	protected FlowableFluid fluid;

	@Inject(method = "receiveNeighborFluids", at = @At("HEAD"))
	private void injectedCollisions(World world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if (this.fluid instanceof AdvancedFlowableFluid advanced) {
			for (Direction direction : FluidBlock.FLOW_DIRECTIONS) {
				advanced.neighborCollision(world, pos, direction, pos.offset(direction));
			}
		}
	}
}
