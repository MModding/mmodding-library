package com.mmodding.library.item.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mmodding.library.item.api.catalog.AdvancedBowItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerEntityMixin {

	@ModifyExpressionValue(method = "getProjectile", at = @At(value = "FIELD", target = "Lnet/minecraft/world/item/Items;ARROW:Lnet/minecraft/world/item/Item;", opcode = Opcodes.GETSTATIC))
	private Item getArrowType(Item original, ItemStack stack) {
		return stack.getItem() instanceof AdvancedBowItem advancedBowItem ? advancedBowItem.getDefaultArrowItem() : original;
	}
}
