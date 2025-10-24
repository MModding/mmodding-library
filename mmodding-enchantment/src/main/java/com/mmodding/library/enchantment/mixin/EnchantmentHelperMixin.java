package com.mmodding.library.enchantment.mixin;

import com.google.common.collect.Iterators;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.library.enchantment.api.AdvancedEnchantment;
import com.mmodding.library.enchantment.api.family.EnchantmentFamily;
import com.mmodding.library.item.api.catalog.EnchantableBookItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Iterator;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

	@WrapOperation(method = "getPossibleEntries", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
	private static boolean addCustomBooks(ItemStack stack, Item item, Operation<Boolean> operation) {
		return operation.call(stack, item) || stack.getItem() instanceof EnchantableBookItem;
	}

	@ModifyExpressionValue(method = "getPossibleEntries", at = @At(value = "INVOKE", target = "Lnet/minecraft/registry/Registry;iterator()Ljava/util/Iterator;"))
	private static Iterator<Enchantment> filterPossibleEntries(Iterator<Enchantment> original, int i, ItemStack itemStack, boolean bl) {
		return Iterators.filter(original, enchantment -> {
			if (enchantment instanceof AdvancedEnchantment advancedEnchantment) {
				EnchantmentFamily enchantmentFamily = advancedEnchantment.getFamily();
				return enchantmentFamily.isObtainableInEnchantingTable() && itemStack.isOf(enchantmentFamily.getBookItem());
			}
			else {
				return true;
			}
		});
	}
}
