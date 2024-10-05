package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;
import com.mmodding.mmodding_lib.library.items.settings.ItemPostHit;
import com.mmodding.mmodding_lib.library.items.settings.ItemPostMine;
import com.mmodding.mmodding_lib.library.items.tools.BreakableTool;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(MiningToolItem.class)
public abstract class MiningToolItemMixin extends ItemMixin {

	@Inject(method = "getMiningSpeedMultiplier", at = @At(value = "HEAD"), cancellable = true)
	private void cancelIfBroken(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
		if (this.getObject() instanceof BreakableTool tool && tool.isBroken(stack)) {
			cir.setReturnValue(1.0f);
		}
	}

	@WrapOperation(method = "postHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V"))
	private void cancelHitStackDamageIfBroken(ItemStack instance, int amount, LivingEntity entity, Consumer<LivingEntity> breakCallback, Operation<Void> original) {
		if (!(this.getObject() instanceof BreakableTool tool) || !tool.isBroken(instance)) {
			original.call(instance, amount, entity, breakCallback);
		}
	}

	@WrapOperation(method = "postHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V"))
	private void cancelIfBroken(ItemStack instance, int amount, LivingEntity entity, Consumer<LivingEntity> breakCallback, Operation<Void> original) {
		if (!(this.getObject() instanceof BreakableTool tool) || !tool.isBroken(instance)) {
			original.call(instance, amount, entity, breakCallback);
		}
	}

	@Inject(method = "postHit", at = @At("TAIL"))
	private void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker, CallbackInfoReturnable<Boolean> cir) {
		ItemPostHit itemPostHit = AdvancedItemSettings.ITEM_POST_HIT.get((MiningToolItem) (Object) this);
		if (itemPostHit != null) itemPostHit.apply(stack, target, attacker);
	}

	@WrapOperation(method = "postMine", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V"))
	private void cancelMineStackDamageIfBroken(ItemStack instance, int amount, LivingEntity entity, Consumer<LivingEntity> breakCallback, Operation<Void> original) {
		if (!(this.getObject() instanceof BreakableTool tool) || !tool.isBroken(instance)) {
			original.call(instance, amount, entity, breakCallback);
		}
	}

	@Inject(method = "postMine", at = @At("TAIL"))
	private void postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> cir) {
		ItemPostMine itemPostMine = AdvancedItemSettings.ITEM_POST_MINE.get((MiningToolItem) (Object) this);
		if (itemPostMine != null) itemPostMine.apply(stack, world, state, pos, miner);
	}

	@Override
	public boolean isSuitableFor(ItemStack stack, BlockState state) {
		if (this.getObject() instanceof BreakableTool tool && tool.isBroken(stack)) {
			return false;
		}
		else {
			return super.isSuitableFor(stack, state);
		}
	}
}
