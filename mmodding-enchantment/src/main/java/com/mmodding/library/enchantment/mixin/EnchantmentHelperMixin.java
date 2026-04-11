package com.mmodding.library.enchantment.mixin;

import com.google.common.collect.Iterators;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.library.enchantment.api.AdvancedEnchantment;
import com.mmodding.library.enchantment.api.family.EnchantmentFamily;
import com.mmodding.library.item.api.catalog.EnchantableBookItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Iterator;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

	@WrapOperation(method = "getAvailableEnchantmentResults", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
	private static boolean addCustomBooks(ItemStack stack, Item item, Operation<Boolean> operation) {
		return operation.call(stack, item) || stack.getItem() instanceof EnchantableBookItem;
	}

	@ModifyExpressionValue(method = "getAvailableEnchantmentResults", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Registry;iterator()Ljava/util/Iterator;"))
	private static Iterator<Enchantment> filterPossibleEntries(Iterator<Enchantment> original, int i, ItemStack itemStack, boolean bl) {
		return Iterators.filter(original, enchantment -> {
			if (enchantment instanceof AdvancedEnchantment advancedEnchantment) {
				EnchantmentFamily enchantmentFamily = advancedEnchantment.getFamily();
				return enchantmentFamily.isObtainableInEnchantingTable() && itemStack.is(enchantmentFamily.getBookItem());
			}
			else {
				return true;
			}
		});
	}
}
