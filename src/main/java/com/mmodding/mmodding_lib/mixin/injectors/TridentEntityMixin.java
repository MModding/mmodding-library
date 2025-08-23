package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.entities.projectiles.SpearEntity;
import net.minecraft.entity.projectile.TridentEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(TridentEntity.class)
public class TridentEntityMixin {

	@ModifyConstant(method = "onEntityHit", constant = @Constant(floatValue = 8.0f))
	private float changeBaseDamage(float constant) {
		if (((Object) this) instanceof SpearEntity spear) {
			return spear.getBaseDamage();
		}
		else {
			return constant;
		}
	}
}
