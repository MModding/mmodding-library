package com.mmodding.library.enchantment.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.library.enchantment.api.family.EnchantmentFamily;
import com.mmodding.library.enchantment.impl.family.EnchantmentLinkRegistryImpl;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

	@WrapOperation(method = "getComponentType", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Ljava/lang/Object;)Z"))
	private static boolean addCustomBooks(ItemStack instance, Object o, Operation<Boolean> original) {
		return original.call(instance, o) || EnchantmentLinkRegistryImpl.isEnchanted(instance);
	}

	@WrapOperation(method = "createBook", at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/item/ItemStack;"))
	private static ItemStack createCustomBook(ItemLike item, Operation<ItemStack> original, @Local(argsOnly = true, name = "enchant") EnchantmentInstance enchant) {
		ResourceKey<Enchantment> enchantmentKey = enchant.enchantment().unwrapKey().orElseThrow();
		if (EnchantmentFamily.REGISTRY.contains(enchantmentKey)) {
			EnchantmentFamily family = EnchantmentFamily.REGISTRY.get(enchantmentKey);
			return original.call(family.getEnchantedBookItem());
		}
		else {
			return original.call(item);
		}
	}

	@WrapOperation(method = "enchantItem(Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/item/ItemStack;ILjava/util/stream/Stream;)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Ljava/lang/Object;)Z"))
	private static boolean checkForCustom(ItemStack instance, Object o, Operation<Boolean> original) {
		return original.call(instance, o) || EnchantmentLinkRegistryImpl.isSource(instance);
	}

	@WrapOperation(method = "enchantItem(Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/item/ItemStack;ILjava/util/stream/Stream;)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/item/ItemStack;"))
	private static ItemStack applyEnchanted(ItemLike item, Operation<ItemStack> original, @Local(argsOnly = true, name = "itemStack") ItemStack itemStack) {
		if (EnchantmentLinkRegistryImpl.isSource(itemStack)) {
			return original.call(EnchantmentLinkRegistryImpl.getEnchanted(itemStack));
		}
		else {
			return original.call(item);
		}
	}

	@WrapOperation(method = "getAvailableEnchantmentResults", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Ljava/lang/Object;)Z"))
	private static boolean expandAvailableCondition(ItemStack instance, Object o, Operation<Boolean> original) {
		return original.call(instance, o) || EnchantmentLinkRegistryImpl.isSource(instance);
	}

	@WrapMethod(method = "lambda$getAvailableEnchantmentResults$0")
	private static boolean filterPossibleEntries(ItemStack itemStack, boolean isBook, Holder<Enchantment> enchantment, Operation<Boolean> original) {
		ResourceKey<Enchantment> enchantmentKey = enchantment.unwrapKey().orElseThrow();
		if (EnchantmentFamily.REGISTRY.contains(enchantmentKey)) {
			EnchantmentFamily family = EnchantmentFamily.REGISTRY.get(enchantmentKey);
			return family.isObtainableInEnchantingTable() && itemStack.is(family.getEnchantedBookItem());
		}
		else {
			return original.call(itemStack, isBook, enchantment);
		}
	}
}
