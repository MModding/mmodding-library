package com.mmodding.library.item.mixin;

import com.mmodding.library.item.api.catalog.SimpleFishingRodItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin extends EntityMixin {

	@Inject(method = "removeIfInvalid", at = @At(value = "HEAD"), cancellable = true)
	private void removeIfInvalid(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
		boolean hasRodInMainHand = (player.getMainHandStack().getItem() instanceof SimpleFishingRodItem);
		boolean hasRodInOffHand = (player.getOffHandStack().getItem() instanceof SimpleFishingRodItem);
		if (!player.isRemoved() && player.isAlive() && (hasRodInMainHand || hasRodInOffHand) && !(this.squaredDistanceTo(player) > 1024.0)) {
			cir.setReturnValue(false);
		}
	}
}
