package com.mmodding.library.item.mixin;

import com.mmodding.library.item.api.catalog.SimpleFishingRodItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingHook.class)
public abstract class FishingBobberEntityMixin extends EntityMixin {

	@Inject(method = "shouldStopFishing", at = @At(value = "HEAD"), cancellable = true)
	private void removeIfInvalid(Player player, CallbackInfoReturnable<Boolean> cir) {
		boolean hasRodInMainHand = (player.getMainHandItem().getItem() instanceof SimpleFishingRodItem);
		boolean hasRodInOffHand = (player.getOffhandItem().getItem() instanceof SimpleFishingRodItem);
		if (!player.isRemoved() && player.isAlive() && (hasRodInMainHand || hasRodInOffHand) && !(this.distanceToSqr(player) > 1024.0)) {
			cir.setReturnValue(false);
		}
	}
}
