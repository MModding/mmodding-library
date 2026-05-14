package com.mmodding.library.block.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mmodding.library.block.api.catalog.SimpleBedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {

	@Shadow
	public abstract ServerLevel level();

	// This is not being dealt with through the use of the BedBlock class. So we need to take care of it aside from the rest.
	@SuppressWarnings("unchecked")
	@ModifyExpressionValue(method = "startSleepInBed", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;", ordinal = 0))
	private <T> Comparable<T> invertHorizontalFacingIfSimpleBed(Comparable<T> original, BlockPos pos) {
		Direction direction = (Direction) original;
		return (Comparable<T>) (this.level().getBlockState(pos).getBlock() instanceof SimpleBedBlock ? direction.getOpposite() : direction);
	}
}
