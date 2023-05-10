package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.items.settings.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
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
	public abstract boolean isFood();

	@Inject(method = "hasGlint", at = @At("TAIL"), cancellable = true)
	private void hasGlint(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (AdvancedItemSettings.GLINT.get((Item) (Object) this)) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "use", at = @At("HEAD"))
	private void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
		ItemUse itemUse = AdvancedItemSettings.ITEM_USE.get((Item) (Object) this);
		if (itemUse != null) {
			itemUse.apply(world, user, hand);
		}
	}

	@Inject(method = "finishUsing", at = @At("HEAD"), cancellable = true)
	private void finishUsing(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
		ItemFinishUsing itemFinishUsing = AdvancedItemSettings.ITEM_FINISH_USING.get((Item) (Object) this);
		if (itemFinishUsing != null) {
			cir.setReturnValue(itemFinishUsing.apply(this.isFood() ? user.eatFood(world, stack) : stack, world, user));
		}
	}

	@Inject(method = "useOnBlock", at = @At("HEAD"))
	private void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		ItemUseOnBlock itemUseOnBlock = AdvancedItemSettings.ITEM_USE_ON_BLOCK.get((Item) (Object) this);
		if (itemUseOnBlock != null) {
			itemUseOnBlock.apply(context);
		}
	}

	@Inject(method = "useOnEntity", at = @At("HEAD"))
	private void useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		ItemUseOnEntity itemUseOnEntity = AdvancedItemSettings.ITEM_USE_ON_ENTITY.get((Item) (Object) this);
		if (itemUseOnEntity != null) {
			itemUseOnEntity.apply(stack, user, entity, hand);
		}
	}

	@Inject(method = "postHit", at = @At("TAIL"))
	private void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker, CallbackInfoReturnable<Boolean> cir) {
		ItemPostHit itemPostHit = AdvancedItemSettings.ITEM_POST_HIT.get((Item) (Object) this);
		if (itemPostHit != null) itemPostHit.apply(stack, target, attacker);
	}

	@Inject(method = "postMine", at = @At("TAIL"))
	private void postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> cir) {
		ItemPostMine itemPostMine = AdvancedItemSettings.ITEM_POST_MINE.get((Item) (Object) this);
		if (itemPostMine != null) itemPostMine.apply(stack, world, state, pos, miner);
	}

	@Inject(method = "getUseAction", at = @At("TAIL"), cancellable = true)
	private void getUseAction(ItemStack stack, CallbackInfoReturnable<UseAction> cir) {
		boolean eatable = AdvancedItemSettings.EATABLE.get((Item) (Object) this);
		if (eatable) cir.setReturnValue(UseAction.EAT);
		boolean drinkable = AdvancedItemSettings.DRINKABLE.get((Item) (Object) this);
		if (drinkable) cir.setReturnValue(UseAction.DRINK);
	}
}
