package com.mmodding.library.item.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mmodding.library.item.api.catalog.AdvancedBowItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

	@ModifyExpressionValue(method = "getProjectileType", at = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;ARROW:Lnet/minecraft/item/Item;", opcode = Opcodes.GETSTATIC))
	private Item getArrowType(Item original, ItemStack stack) {
		return stack.getItem() instanceof AdvancedBowItem advancedBowItem ? advancedBowItem.getDefaultArrowItem() : original;
	}
}
