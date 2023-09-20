package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.fluids.buckets.CustomBucketItem;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CowEntity.class)
public abstract class CowEntityMixin extends AnimalEntityMixin {

	@Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
	private void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack stack = player.getStackInHand(hand);
		if (stack.getItem() instanceof CustomBucketItem bucket) {
			player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0f, 1.0f);
			ItemStack check = bucket.getManager().getFilledItem(new ItemStack(Items.MILK_BUCKET));
			if (!check.isEmpty()) {
				ItemStack result = ItemUsage.exchangeStack(stack, player, check);
				player.setStackInHand(hand, result);
				cir.setReturnValue(ActionResult.success(this.world.isClient));
			}
			else {
				cir.setReturnValue(super.interactMob(player, hand));
			}
		}
	}
}
