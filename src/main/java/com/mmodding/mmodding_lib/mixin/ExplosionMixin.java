package com.mmodding.mmodding_lib.mixin;

import com.mmodding.mmodding_lib.library.DamageSources;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {

	@Shadow
	public abstract DamageSource getDamageSource();

	@Redirect(method = "collectBlocksAndDamageEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
	private boolean injected(Entity entity, DamageSource source, float amount) {
		if (this.getDamageSource() != DamageSources.PUSH) {
			return entity.damage(source, amount);
		}
		return false;
	}
}
