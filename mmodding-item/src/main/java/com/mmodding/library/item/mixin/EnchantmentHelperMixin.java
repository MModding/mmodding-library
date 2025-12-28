package com.mmodding.library.item.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.library.item.api.catalog.EnchantableBookItem;
import com.mmodding.library.item.api.catalog.SimpleEnchantedBookItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

	@WrapOperation(method = "get", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
	private static boolean allowGetModdedEnchantedBookItems(ItemStack instance, Item item, Operation<Boolean> original) {
		return instance.getItem() instanceof SimpleEnchantedBookItem || original.call(instance, item);
	}

	@WrapOperation(method = "set", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
	private static boolean allowSetModdedEnchantedBookItems(ItemStack instance, Item item, Operation<Boolean> original) {
		return instance.getItem() instanceof SimpleEnchantedBookItem || original.call(instance, item);
	}

	@WrapOperation(method = "enchant", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
	private static boolean allowEnchantableBookItems(ItemStack instance, Item item, Operation<Boolean> original) {
		return instance.getItem() instanceof EnchantableBookItem || original.call(instance, item);
	}
}
