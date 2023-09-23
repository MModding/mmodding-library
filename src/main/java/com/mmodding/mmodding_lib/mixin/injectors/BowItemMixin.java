package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mmodding.mmodding_lib.library.items.CustomBowItem;
import com.mmodding.mmodding_lib.library.utils.Self;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BowItem.class)
public class BowItemMixin implements Self<BowItem> {

	@ModifyExpressionValue(method = "onStoppedUsing", at = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;ARROW:Lnet/minecraft/item/Item;", opcode = Opcodes.GETSTATIC))
	private Item onStoppedUsing(Item original) {
		return this.getObject() instanceof CustomBowItem customBowItem ? customBowItem.getDefaultArrowItem() : original;
	}
}
