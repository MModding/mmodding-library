package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.tags.MModdingBlockTags;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnimalEntity.class)
public class AnimalEntityMixin {

	@Inject(method = "getPathfindingFavor", at = @At("TAIL"), cancellable = true)
	private void getPathfindingFavor(BlockPos pos, WorldView world, CallbackInfoReturnable<Float> cir) {
		if (world.getBlockState(pos.down()).isIn(MModdingBlockTags.ANIMAL_PATHFINDINGS)) {
			cir.setReturnValue(10.0f);
		}
	}
}
