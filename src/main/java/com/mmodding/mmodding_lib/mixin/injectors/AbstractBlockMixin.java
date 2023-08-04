package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.AbstractBlockSettingsDuckInterface;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin {

	@Unique
	public AbstractBlockSettingsDuckInterface duckedSettings() {
		return (AbstractBlockSettingsDuckInterface) this.settings;
	}

	@Shadow
	@Final
	protected AbstractBlock.Settings settings;

	@Shadow
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		return null;
	}

	@Inject(method = "isSideInvisible", at = @At("HEAD"), cancellable = true)
	private void isSideInvisible(BlockState state, BlockState stateFrom, Direction direction, CallbackInfoReturnable<Boolean> cir) {
		AbstractBlock thisObject = (AbstractBlock) (Object) this;
		if (thisObject instanceof Block thisBlock) {
			if (this.duckedSettings().mmodding_lib$getInvisibleSides() && stateFrom.isOf(thisBlock)) {
				cir.setReturnValue(true);
			}
		}
	}
}
