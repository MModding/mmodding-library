package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.items.CustomFishingRodItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin extends EntityMixin {

	@Inject(method = "removeIfInvalid", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/FishingBobberEntity;discard()V", shift = At.Shift.BEFORE), cancellable = true)
	private void removeIfInvalid(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
		boolean hasRodInMainHand = (player.getMainHandStack().getItem() instanceof CustomFishingRodItem);
		boolean hasRodInOffHand = (player.getOffHandStack().getItem() instanceof CustomFishingRodItem);
		if (!player.isRemoved() && player.isAlive() && (hasRodInMainHand || hasRodInOffHand) && !(this.squaredDistanceTo(player) > 1024.0)) {
			cir.setReturnValue(false);
		}
	}
}
