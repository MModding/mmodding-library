package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.interface_injections.TagRuntimeManagement;
import com.mmodding.mmodding_lib.library.blocks.CustomBedBlock;
import com.mmodding.mmodding_lib.library.blocks.CustomLeavesBlock;
import com.mmodding.mmodding_lib.library.tags.modifiers.TagModifier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tag.TagKey;
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
public abstract class AbstractBlockStateMixin implements TagRuntimeManagement.BlockStateTagRuntimeManagement {

	@Shadow
	public abstract Block getBlock();

	@Shadow
	public abstract boolean isIn(TagKey<Block> tag);

	@Shadow
	protected abstract BlockState asBlockState();

	@Inject(method = "hasBlockEntity", at = @At("HEAD"), cancellable = true)
	private void injectCustomBedBlockBehavior(CallbackInfoReturnable<Boolean> cir) {
		if (this.getBlock() instanceof CustomBedBlock customBedBlock && !customBedBlock.hasBlockEntity()) {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "updateNeighbors(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;II)V", at = @At("TAIL"))
	private void injectIfCustomLeavesBlockThatDoNotHaveConnectedLeaves(WorldAccess world, BlockPos pos, int flags, int maxUpdateDepth, CallbackInfo ci) {
		// Those Leaves need to update their neighbors in a 3x3x3 cube
		if (this.getBlock() instanceof CustomLeavesBlock leaves && !leaves.areLeavesConnected()) {
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
							world.method_42308(Direction.NORTH, this.asBlockState(), pos.add(i, j, k), pos, flags, maxUpdateDepth);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean isIn(TagModifier<Block> modifier) {
		return modifier.result(this.getBlock(), this::isIn);
	}
}
