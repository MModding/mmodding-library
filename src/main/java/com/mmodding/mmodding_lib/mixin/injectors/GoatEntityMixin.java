package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.fluids.buckets.CustomMilkBucketItem;
import com.mmodding.mmodding_lib.library.utils.Self;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GoatEntity.class)
public abstract class GoatEntityMixin extends AnimalEntityMixin implements Self<GoatEntity> {

	@Shadow
	public abstract SoundEvent getEatSound(ItemStack stack);

	@Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
	private void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		ItemStack stack = player.getStackInHand(hand);
		if (stack.getItem() instanceof CustomMilkBucketItem bucket) {
			player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0f, 1.0f);
			ItemStack check = bucket.getManager().getFilledItem(new ItemStack(Items.MILK_BUCKET));
			if (!check.isEmpty()) {
				ItemStack result = ItemUsage.exchangeStack(stack, player, check);
				player.setStackInHand(hand, result);
				cir.setReturnValue(ActionResult.success(this.world.isClient));
			}
			else {
				ActionResult actionResult = super.interactMob(player, hand);
				if (actionResult.isAccepted() && this.isBreedingItem(stack)) {
					this.world.playSoundFromEntity(
						null,
						this.getObject(),
						this.getEatSound(stack),
						SoundCategory.NEUTRAL,
						1.0F,
						MathHelper.nextBetween(this.world.random, 0.8f, 1.2f)
					);
				}
				cir.setReturnValue(actionResult);
			}
		}
	}
}
