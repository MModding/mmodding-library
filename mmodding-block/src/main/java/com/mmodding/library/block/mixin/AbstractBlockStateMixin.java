package com.mmodding.library.block.mixin;

import com.mmodding.library.block.api.catalog.AdvancedLeavesBlock;
import com.mmodding.library.block.api.catalog.SimpleBedBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {

	@Shadow
	public abstract Block getBlock();

	@Shadow
	protected abstract BlockState asBlockState();

	@Inject(method = "hasBlockEntity", at = @At("HEAD"), cancellable = true)
	private void injectSimpleBedBlockBehavior(CallbackInfoReturnable<Boolean> cir) {
		if (this.getBlock() instanceof SimpleBedBlock) {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "updateNeighbors(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;II)V", at = @At("TAIL"))
	private void injectIfAdvancedLeavesBlockThatDoNotHaveConnectedLeaves(WorldAccess world, BlockPos pos, int flags, int maxUpdateDepth, CallbackInfo ci) {
		// Those Leaves need to update their neighbors in a 3x3x3 cube
		if (this.getBlock() instanceof AdvancedLeavesBlock leaves && !leaves.areLeavesConnected()) {
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					for (int k = -1; k <= 1; k++) {
						boolean bl = true; // Is not already updated
						for (Direction direction : Direction.values()) {
							if (pos.add(i, j, k).equals(pos.offset(direction))) {
								bl = false;
							}
						}
						if (bl) {
							world.replaceWithStateForNeighborUpdate(Direction.NORTH, this.asBlockState(), pos.add(i, j, k), pos, flags, maxUpdateDepth);
						}
					}
				}
			}
		}
	}
}
