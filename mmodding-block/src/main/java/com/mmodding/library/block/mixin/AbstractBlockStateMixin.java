package com.mmodding.library.block.mixin;

import com.mmodding.library.block.api.catalog.AdvancedLeavesBlock;
import com.mmodding.library.block.api.catalog.SimpleBedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class AbstractBlockStateMixin {

	@Shadow
	public abstract Block getBlock();

	@Shadow
	protected abstract BlockState asState();

	@Inject(method = "hasBlockEntity", at = @At("HEAD"), cancellable = true)
	private void injectSimpleBedBlockBehavior(CallbackInfoReturnable<Boolean> cir) {
		if (this.getBlock() instanceof SimpleBedBlock) {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "updateNeighbourShapes(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;II)V", at = @At("TAIL"))
	private void injectIfAdvancedLeavesBlockThatDoNotHaveConnectedLeaves(LevelAccessor world, BlockPos pos, int flags, int maxUpdateDepth, CallbackInfo ci) {
		// Those Leaves need to update their neighbors in a 3x3x3 cube
		if (this.getBlock() instanceof AdvancedLeavesBlock leaves && !leaves.areLeavesConnected()) {
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					for (int k = -1; k <= 1; k++) {
						boolean bl = true; // Is not already updated
						for (Direction direction : Direction.values()) {
							if (pos.offset(i, j, k).equals(pos.relative(direction))) {
								bl = false;
							}
						}
						if (bl) {
							world.neighborShapeChanged(Direction.NORTH, this.asState(), pos.offset(i, j, k), pos, flags, maxUpdateDepth);
						}
					}
				}
			}
		}
	}
}
