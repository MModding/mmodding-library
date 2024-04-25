package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.mmodding_lib.library.items.CustomSpearItem;
import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;
import com.mmodding.mmodding_lib.library.items.settings.ItemPostHit;
import com.mmodding.mmodding_lib.library.items.settings.ItemPostMine;
import com.mmodding.mmodding_lib.library.utils.Self;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TridentItem.class)
public class TridentItemMixin implements Self<TridentItem> {

	@Inject(method = "postHit", at = @At("TAIL"))
	private void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker, CallbackInfoReturnable<Boolean> cir) {
		ItemPostHit itemPostHit = AdvancedItemSettings.ITEM_POST_HIT.get((TridentItem) (Object) this);
		if (itemPostHit != null) itemPostHit.apply(stack, target, attacker);
	}

	@Inject(method = "postMine", at = @At("TAIL"))
	private void postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> cir) {
		ItemPostMine itemPostMine = AdvancedItemSettings.ITEM_POST_MINE.get((TridentItem) (Object) this);
		if (itemPostMine != null) itemPostMine.apply(stack, world, state, pos, miner);
	}

	@WrapOperation(method = "onStoppedUsing", at = @At(value = "NEW", target = "(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/entity/projectile/TridentEntity;"))
	private TridentEntity useSpearConditionally(World world, LivingEntity owner, ItemStack stack, Operation<TridentEntity> original) {
		if (this.getObject() instanceof CustomSpearItem spear && spear.getLaunchFactory() != null) {
			return spear.getLaunchFactory().create(world, owner, stack);
		}
		else {
			return original.call(world, owner, stack);
		}
	}
}
