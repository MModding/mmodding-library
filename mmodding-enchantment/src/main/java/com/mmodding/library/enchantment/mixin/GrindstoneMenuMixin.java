package com.mmodding.library.enchantment.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.library.enchantment.impl.family.EnchantmentLinkRegistryImpl;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GrindstoneMenu.class)
public class GrindstoneMenuMixin {

	@WrapOperation(method = "removeNonCursesFrom", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Ljava/lang/Object;)Z"))
	private boolean expandBookItems(ItemStack instance, Object o, Operation<Boolean> original) {
		return original.call(instance, o) || EnchantmentLinkRegistryImpl.isEnchanted(instance);
	}

	@WrapOperation(method = "removeNonCursesFrom", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;transmuteCopy(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/item/ItemStack;"))
	private ItemStack computeEnchanted(ItemStack instance, ItemLike newItem, Operation<ItemStack> original) {
		if (EnchantmentLinkRegistryImpl.isEnchanted(instance)) {
			return original.call(instance, EnchantmentLinkRegistryImpl.getSource(instance));
		}
		else {
			return original.call(instance, newItem);
		}
	}
}
