package com.mmodding.library.enchantment.mixin;

import com.mmodding.library.enchantment.api.family.EnchantmentFamily;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

	@Inject(method = "areCompatible", at = @At("HEAD"), cancellable = true)
	private static void insertFamilyCompatibilities(Holder<Enchantment> enchantment, Holder<Enchantment> other, CallbackInfoReturnable<Boolean> cir) {
		ResourceKey<Enchantment> sourceKey = enchantment.unwrapKey().orElseThrow();
		ResourceKey<Enchantment> otherKey = enchantment.unwrapKey().orElseThrow();
		EnchantmentFamily sourceFamily = EnchantmentFamily.REGISTRY.contains(sourceKey) ? EnchantmentFamily.REGISTRY.get(sourceKey) : EnchantmentFamily.DEFAULT;
		EnchantmentFamily otherFamily = EnchantmentFamily.REGISTRY.contains(otherKey) ? EnchantmentFamily.REGISTRY.get(otherKey) : EnchantmentFamily.DEFAULT;
		if (sourceFamily != otherFamily) {
			if (!sourceFamily.getFamilyCompatibilities().check(otherFamily) || !otherFamily.getFamilyCompatibilities().check(sourceFamily)) {
				cir.setReturnValue(false);
			}
		}
	}

	@Inject(method = "getFullname", at = @At("HEAD"), cancellable = true)
	private static void changeEnchantmentDescription(Holder<Enchantment> enchantment, int level, CallbackInfoReturnable<Component> cir) {
		ResourceKey<Enchantment> resourceKey = enchantment.unwrapKey().orElseThrow();
		if (EnchantmentFamily.REGISTRY.contains(resourceKey)) {
			EnchantmentFamily family = EnchantmentFamily.REGISTRY.get(resourceKey);
			MutableComponent result = enchantment.value().description().copy();
			family.getFormattings(enchantment).forEach(result::withStyle);
			if (level != 1 || enchantment.value().getMaxLevel() != 1) {
				result.append(CommonComponents.SPACE).append(Component.translatable("enchantment.level." + level));
			}
			cir.setReturnValue(result);
		}
	}
}
