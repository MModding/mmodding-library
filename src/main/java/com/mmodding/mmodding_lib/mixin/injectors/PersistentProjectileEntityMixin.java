package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.mmodding_lib.ducks.LivingEntityDuckInterface;
import com.mmodding.mmodding_lib.library.utils.Self;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public class PersistentProjectileEntityMixin implements Self<PersistentProjectileEntity> {

	@Inject(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setStuckArrowCount(I)V"))
	private void onEntityHit(EntityHitResult entityHitResult, CallbackInfo ci, @Local LivingEntity livingEntity) {
		((LivingEntityDuckInterface) livingEntity).mmodding_lib$addStuckArrowType(Registry.ENTITY_TYPE.getId(this.getObject().getType()));
	}
}
