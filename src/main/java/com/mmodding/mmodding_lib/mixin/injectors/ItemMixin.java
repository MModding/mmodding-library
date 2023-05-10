package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.items.CustomItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {

	@Shadow
	public abstract ItemStack finishUsing(ItemStack stack, World world, LivingEntity user);

	@Shadow
	public abstract boolean isFood();

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
			cir.setReturnValue(itemFinishUsing.apply(this.isFood() ? user.eatFood(world, stack) : stack, world, user));
		}
	}

	@Inject(method = "postHit", at = @At("TAIL"))
	private void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker, CallbackInfoReturnable<Boolean> cir) {
		CustomItemSettings.ItemPostHit itemPostHit = CustomItemSettings.ITEM_POST_HIT.get((Item) (Object) this);
		if (itemPostHit != null) itemPostHit.apply(stack, target, attacker);
	}

	@Inject(method = "postMine", at = @At("TAIL"))
	private void postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> cir) {
		CustomItemSettings.ItemPostMine itemPostMine = CustomItemSettings.ITEM_POST_MINE.get((Item) (Object) this);
		if (itemPostMine != null) itemPostMine.apply(stack, world, state, pos, miner);
	}

	@Inject(method = "getUseAction", at = @At("TAIL"), cancellable = true)
	private void getUseAction(ItemStack stack, CallbackInfoReturnable<UseAction> cir) {
		boolean eatable = CustomItemSettings.EATABLE.get((Item) (Object) this);
		if (eatable) cir.setReturnValue(UseAction.EAT);
		boolean drinkable = CustomItemSettings.DRINKABLE.get((Item) (Object) this);
		if (drinkable) cir.setReturnValue(UseAction.DRINK);
	}
}
