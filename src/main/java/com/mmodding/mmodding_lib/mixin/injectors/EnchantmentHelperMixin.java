package com.mmodding.mmodding_lib.mixin.injectors;

import com.google.common.collect.Iterators;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mmodding.mmodding_lib.library.items.CustomBookItem;
import com.mmodding.mmodding_lib.library.enchantments.CustomEnchantment;
import com.mmodding.mmodding_lib.library.enchantments.types.EnchantmentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Iterator;
import java.util.function.Predicate;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

	@WrapOperation(method = "getPossibleEntries", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
	private static boolean addCustomBooks(ItemStack stack, Item item, Operation<Boolean> operation, @Share("type") LocalRef<EnchantmentType> type) {
		type.set(stack.getItem() instanceof CustomBookItem book ? book.getType() : EnchantmentType.DEFAULT);
		return operation.call(stack, item) || stack.getItem() instanceof CustomBookItem;
	}

	@ModifyExpressionValue(method = "getPossibleEntries", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/registry/Registry;iterator()Ljava/util/Iterator;"))
	private static Iterator<Enchantment> filterPossibleEntries(Iterator<Enchantment> original, @Share("type") LocalRef<EnchantmentType> type) {
		Predicate<EnchantmentType> isTypeValid = enchantmentType -> enchantmentType.canBeObtainedThroughEnchantingTable() && enchantmentType == type.get();
		return Iterators.filter(
			original, enchantment -> !(enchantment instanceof CustomEnchantment customEnchantment) || isTypeValid.test(customEnchantment.getType())
		);
	}
}
