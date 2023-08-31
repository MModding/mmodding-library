package com.mmodding.mmodding_lib.mixin.injectors;

import com.google.common.collect.Iterators;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mmodding.mmodding_lib.library.enchantments.CustomEnchantment;
import com.mmodding.mmodding_lib.library.enchantments.types.EnchantmentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.EnchantedBookItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Iterator;

@Mixin(EnchantedBookItem.class)
public class EnchantedBookItemMixin {

    @ModifyExpressionValue(method = "appendStacks", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/registry/Registry;iterator()Ljava/util/Iterator;"))
    private Iterator<Enchantment> appendStacks(Iterator<Enchantment> original) {
		return Iterators.filter(
			original, enchantment -> !(enchantment instanceof CustomEnchantment customEnchantment) || customEnchantment.getType() == EnchantmentType.DEFAULT
		);
    }
}
