package com.mmodding.mmodding_lib.mixin;

import com.mmodding.mmodding_lib.library.items.CustomItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {

	@Inject(method = "hasGlint", at = @At("TAIL"), cancellable = true)
	private void hasGlint(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (CustomItemSettings.GLINT.get((Item) (Object) this)) {
			cir.setReturnValue(true);
		}
	}
}
