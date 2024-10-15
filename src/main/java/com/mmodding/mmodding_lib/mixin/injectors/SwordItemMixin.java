package com.mmodding.mmodding_lib.mixin.injectors;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;
import com.mmodding.mmodding_lib.library.items.settings.ItemPostHit;
import com.mmodding.mmodding_lib.library.items.settings.ItemPostMine;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(SwordItem.class)
public abstract class SwordItemMixin extends ItemMixin {

	@Inject(method = "getMiningSpeedMultiplier", at = @At(value = "HEAD"), cancellable = true)
	private void cancelIfBroken(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
		if (this.isBroken(stack)) {
			cir.setReturnValue(1.0f);
		}
	}

	@WrapOperation(method = "postHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V"))
	private void cancelHitStackDamageIfBroken(ItemStack instance, int amount, LivingEntity entity, Consumer<LivingEntity> breakCallback, Operation<Void> original) {
		if (AdvancedItemSettings.HAS_BROKEN_STATE.get(this.getObject())) {
			instance.setDamage(Math.min(instance.getDamage() + amount, instance.getMaxDamage()));
		}
		else {
			original.call(instance, amount, entity, breakCallback);
		}
	}

	@Inject(method = "postHit", at = @At("TAIL"))
	private void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker, CallbackInfoReturnable<Boolean> cir) {
		ItemPostHit itemPostHit = AdvancedItemSettings.ITEM_POST_HIT.get((SwordItem) (Object) this);
		if (itemPostHit != null) itemPostHit.apply(stack, target, attacker);
	}

	@WrapOperation(method = "postMine", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V"))
	private void cancelMineStackDamageIfBroken(ItemStack instance, int amount, LivingEntity entity, Consumer<LivingEntity> breakCallback, Operation<Void> original) {
		if (AdvancedItemSettings.HAS_BROKEN_STATE.get(this.getObject())) {
			instance.setDamage(Math.min(instance.getDamage() + amount, instance.getMaxDamage()));
		}
		else {
			original.call(instance, amount, entity, breakCallback);
		}
	}

	@Inject(method = "postMine", at = @At("TAIL"))
	private void postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> cir) {
		ItemPostMine itemPostMine = AdvancedItemSettings.ITEM_POST_MINE.get((SwordItem) (Object) this);
		if (itemPostMine != null) itemPostMine.apply(stack, world, state, pos, miner);
	}

	@Override
	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
		if (this.isBroken(stack)) {
			return ImmutableMultimap.of();
		}
		else {
			return super.getAttributeModifiers(stack, slot);
		}
	}

	@Override
	public boolean isSuitableFor(ItemStack stack, BlockState state) {
		if (this.isBroken(stack)) {
			return false;
		}
		else {
			return super.isSuitableFor(stack, state);
		}
	}
}
