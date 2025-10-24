package com.mmodding.library.enchantment.mixin;

import com.mmodding.library.enchantment.api.AdvancedEnchantment;
import com.mmodding.library.enchantment.api.family.EnchantmentFamily;
import com.mmodding.library.java.api.object.Self;
import net.minecraft.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin implements Self<Enchantment> {

	@Inject(method = "canCombine", at = @At("HEAD"), cancellable = true)
	private void insertFamilyCompatibilities(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
		EnchantmentFamily selfFamily = this.getObject() instanceof AdvancedEnchantment enchantment ? enchantment.getFamily() : EnchantmentFamily.DEFAULT;
		EnchantmentFamily otherFamily = other instanceof AdvancedEnchantment enchantment ? enchantment.getFamily() : EnchantmentFamily.DEFAULT;
		if (selfFamily != otherFamily) {
			if (!selfFamily.getFamilyCompatibilities().check(otherFamily) || !otherFamily.getFamilyCompatibilities().check(selfFamily)) {
				cir.setReturnValue(false);
			}
		}
	}
}
