package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.materials.RepairOperations;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ToolItem.class)
public class ToolItemMixin {

	@Shadow
	@Final
	private ToolMaterial material;

	@Inject(method = "canRepair", at = @At("TAIL"), cancellable = true)
	private void applyRepairConditionalPrevention(ItemStack stack, ItemStack ingredient, CallbackInfoReturnable<Boolean> cir) {
		if (this.material instanceof RepairOperations operations) {
			if (operations.preventsRepair(stack)) {
				cir.setReturnValue(false);
			}
		}
	}
}
