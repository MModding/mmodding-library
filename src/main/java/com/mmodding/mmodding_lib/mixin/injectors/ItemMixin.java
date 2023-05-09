package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.items.CustomItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
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

	@Inject(method = "use", at = @At("HEAD"))
	private void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		CustomItemSettings.ItemUseSetting itemUse = CustomItemSettings.ITEM_USE.get((Item) (Object) this);
		if (itemUse != null) {
			itemUse.apply(world, user, hand);
		}
	}

	@Inject(method = "finishUsing", at = @At("HEAD"), cancellable = true)
	private void finishUsing(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
		CustomItemSettings.ItemFinishUsingSetting itemFinishUsing = CustomItemSettings.ITEM_FINISH_USING.get((Item) (Object) this);
		if (itemFinishUsing != null) {
			itemFinishUsing.apply(stack, world, user, cir);
		}
	}

	@Inject(method = "getUseAction", at = @At("TAIL"), cancellable = true)
	private void getUseAction(ItemStack stack, CallbackInfoReturnable<UseAction> cir) {
		boolean eatable = CustomItemSettings.EATABLE.get((Item) (Object) this);
		if (eatable) cir.setReturnValue(UseAction.EAT);
		boolean drinkable = CustomItemSettings.DRINKABLE.get((Item) (Object) this);
		if (drinkable) cir.setReturnValue(UseAction.DRINK);
	}
}
