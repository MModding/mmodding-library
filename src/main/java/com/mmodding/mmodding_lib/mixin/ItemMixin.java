package com.mmodding.mmodding_lib.mixin;

import com.mmodding.mmodding_lib.library.items.CustomItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
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
		CustomItemSettings.ItemUseSetting setting = CustomItemSettings.ITEM_USE.get((Item) (Object) this);
		if (setting != null) {
			setting.apply(world, user, hand);
		}
	}
}
