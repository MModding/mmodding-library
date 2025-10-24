package com.mmodding.library.enchantment.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.library.enchantment.api.AdvancedEnchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemConvertible;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EnchantedBookItem.class)
public class EnchantedBookItemMixin {

	@ModifyArg(method = "forEnchantment", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;<init>(Lnet/minecraft/item/ItemConvertible;)V"))
	private static ItemConvertible changeEnchantedBook(ItemConvertible itemConvertible, @Local(argsOnly = true) EnchantmentLevelEntry entry) {
		return entry.enchantment instanceof AdvancedEnchantment enchantment ? enchantment.getFamily().getBookItem() : itemConvertible;
	}
}
