package com.mmodding.library.item.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.library.item.api.catalog.EnchantableBookItem;
import com.mmodding.library.item.api.catalog.SimpleEnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

	@WrapOperation(method = "getEnchantments", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
	private static boolean allowGetModdedEnchantedBookItems(ItemStack instance, Item item, Operation<Boolean> original) {
		return instance.getItem() instanceof SimpleEnchantedBookItem || original.call(instance, item);
	}

	@WrapOperation(method = "setEnchantments", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
	private static boolean allowSetModdedEnchantedBookItems(ItemStack instance, Item item, Operation<Boolean> original) {
		return instance.getItem() instanceof SimpleEnchantedBookItem || original.call(instance, item);
	}

	@WrapOperation(method = "enchantItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
	private static boolean allowEnchantableBookItems(ItemStack instance, Item item, Operation<Boolean> original) {
		return instance.getItem() instanceof EnchantableBookItem || original.call(instance, item);
	}
}
