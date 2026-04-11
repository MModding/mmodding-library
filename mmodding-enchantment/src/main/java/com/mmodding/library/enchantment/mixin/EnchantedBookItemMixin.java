package com.mmodding.library.enchantment.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.library.enchantment.api.AdvancedEnchantment;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EnchantedBookItem.class)
public class EnchantedBookItemMixin {

	@ModifyArg(method = "createForEnchantment", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;<init>(Lnet/minecraft/world/level/ItemLike;)V"))
	private static ItemLike changeEnchantedBook(ItemLike itemConvertible, @Local(argsOnly = true) EnchantmentInstance entry) {
		return entry.enchantment instanceof AdvancedEnchantment enchantment ? enchantment.getFamily().getBookItem() : itemConvertible;
	}
}
