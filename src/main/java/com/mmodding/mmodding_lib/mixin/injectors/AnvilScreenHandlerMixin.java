package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.mmodding_lib.library.items.CustomEnchantedBookItem;
import com.mmodding.mmodding_lib.library.materials.RepairOperations;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {

    @WrapOperation(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    private boolean updateResultFirst(ItemStack stack, Item item, Operation<Boolean> original) {
        return stack.getItem() instanceof CustomEnchantedBookItem || original.call(stack, item);
    }

    @WrapOperation(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 2))
    private boolean updateResultSecond(ItemStack stack, Item item, Operation<Boolean> original) {
        return stack.getItem() instanceof CustomEnchantedBookItem || original.call(stack, item);
    }

	@ModifyArg(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/CraftingResultInventory;setStack(ILnet/minecraft/item/ItemStack;)V"), index = 1)
	private ItemStack afterResultOperation(ItemStack stack) {
		RepairOperations operations = null;
		if (stack.getItem() instanceof ArmorItem armorItem) {
			if (armorItem.getMaterial() instanceof RepairOperations repairs) {
				operations = repairs;
			}
		}
		else if (stack.getItem() instanceof ToolItem toolItem) {
			if (toolItem.getMaterial() instanceof RepairOperations repairs) {
				operations = repairs;
			}
		}
		if (operations != null) {
			operations.afterRepaired(stack);
		}
		return stack;
	}
}
