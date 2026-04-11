package com.mmodding.library.item.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.library.item.api.catalog.EnchantableBookItem;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentMenu.class)
public class EnchantmentScreenHandlerMixin {

	@WrapOperation(method = "method_17410", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
	private boolean addEnchantableBooks(ItemStack stack, Item item, Operation<Boolean> operation) {
		return operation.call(stack, item) || stack.getItem() instanceof EnchantableBookItem;
	}

	@ModifyExpressionValue(method = "method_17410", at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/Items;ENCHANTED_BOOK:Lnet/minecraft/world/item/Item;", opcode = Opcodes.GETSTATIC))
	private Item setModdedEnchantedBooks(Item original, ItemStack itemStack) {
		return itemStack.getItem() instanceof EnchantableBookItem enchantableBookItem ? enchantableBookItem.getEnchantedBookItem() : original;
	}

	@WrapOperation(method = "getEnchantmentList", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
	private boolean generateEnchantments(ItemStack stack, Item item, Operation<Boolean> original) {
		return original.call(stack, item) || stack.getItem() instanceof EnchantableBookItem;
	}
}
