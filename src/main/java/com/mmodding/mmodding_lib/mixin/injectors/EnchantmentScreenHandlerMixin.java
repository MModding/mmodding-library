package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mmodding.mmodding_lib.library.enchantments.CustomBookItem;
import com.mmodding.mmodding_lib.library.enchantments.types.EnchantmentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.EnchantmentScreenHandler;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentScreenHandler.class)
public class EnchantmentScreenHandlerMixin {

    @WrapOperation(method = "method_17410", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean addCustomBooks(ItemStack stack, Item item, Operation<Boolean> operation, @Share("type") LocalRef<EnchantmentType> type) {
        type.set(stack.getItem() instanceof CustomBookItem book ? book.getType() : EnchantmentType.DEFAULT);
        return operation.call(stack, item) || stack.getItem() instanceof CustomBookItem;
    }

    @ModifyExpressionValue(method = "method_17410", at = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;ENCHANTED_BOOK:Lnet/minecraft/item/Item;", opcode = Opcodes.GETSTATIC))
    private Item setCustomEnchantedBooks(Item original, @Share("type") LocalRef<EnchantmentType> type) {
        return type.get() != EnchantmentType.DEFAULT ? type.get().getEnchantedBook() : original;
    }

    @WrapOperation(method = "generateEnchantments", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean generateEnchantments(ItemStack stack, Item item, Operation<Boolean> original) {
        return original.call(stack, item) || stack.getItem() instanceof CustomBookItem;
    }
}
