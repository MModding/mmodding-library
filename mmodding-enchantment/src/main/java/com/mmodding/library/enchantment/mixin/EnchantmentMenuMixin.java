package com.mmodding.library.enchantment.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.library.enchantment.impl.family.EnchantmentLinkRegistryImpl;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentMenu.class)
public class EnchantmentMenuMixin {

	@WrapOperation(method = "lambda$clickMenuButton$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Ljava/lang/Object;)Z"))
	private boolean addEnchantableBooks(ItemStack instance, Object o, Operation<Boolean> original) {
		return original.call(instance, o) || EnchantmentLinkRegistryImpl.isSource(instance);
	}

	@ModifyExpressionValue(method = "lambda$clickMenuButton$0", at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/Items;ENCHANTED_BOOK:Lnet/minecraft/world/item/Item;", opcode = Opcodes.GETSTATIC))
	private Item setModdedEnchantedBooks(Item original, ItemStack itemStack) {
		return EnchantmentLinkRegistryImpl.isSource(itemStack) ? EnchantmentLinkRegistryImpl.getEnchanted(itemStack) : original;
	}

	@WrapOperation(method = "getEnchantmentList", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Ljava/lang/Object;)Z"))
	private boolean generateEnchantments(ItemStack instance, Object o, Operation<Boolean> original) {
		return original.call(instance, o) || EnchantmentLinkRegistryImpl.isSource(instance);
	}
}
