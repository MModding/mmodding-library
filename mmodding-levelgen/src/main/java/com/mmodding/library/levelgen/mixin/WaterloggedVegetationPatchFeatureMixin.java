package com.mmodding.library.levelgen.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.library.levelgen.api.feature.catalog.AdvancedLiquidVegetationPatchFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.WaterloggedVegetationPatchFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WaterloggedVegetationPatchFeature.class)
public class WaterloggedVegetationPatchFeatureMixin {

	@WrapOperation(method = "placeGroundPatch", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
	private boolean allowCustomFluid(WorldGenLevel instance, BlockPos blockPos, BlockState blockState, int i, Operation<Boolean> original) {
		if ((Object) this instanceof AdvancedLiquidVegetationPatchFeature advanced) {
			return original.call(instance, blockPos, advanced.liquidState.value().getState(instance, instance.getRandom(), blockPos), i);
		}
		else {
			return original.call(instance, blockPos, blockState, i);
		}
	}
}
