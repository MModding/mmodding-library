package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.containers.AdvancedInventory;
import com.mmodding.mmodding_lib.library.items.CustomItemWithInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public abstract class SlotMixin {

	@Shadow
	public abstract boolean hasStack();

	@Shadow
	public abstract ItemStack getStack();

	@Shadow
	@Final
	public Inventory inventory;

	@Inject(method = "canInsert", at = @At("HEAD"), cancellable = true)
	private void canInsert(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (this.inventory instanceof AdvancedInventory advancedInventory) {
			if (advancedInventory.isNestedFiltered()) cir.setReturnValue(stack.getItem().canBeNested());
		}
	}

	@Inject(method = "canTakeItems", at = @At("HEAD"), cancellable = true)
	private void canTakeItems(PlayerEntity playerEntity, CallbackInfoReturnable<Boolean> cir) {
		if (this.hasStack()) {
			if (this.getStack().getItem() instanceof CustomItemWithInventory item) {
				cir.setReturnValue(!item.isOpened());
			}
		}
	}
}
