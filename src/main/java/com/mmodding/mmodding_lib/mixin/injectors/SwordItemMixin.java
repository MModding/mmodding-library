package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;
import com.mmodding.mmodding_lib.library.items.settings.ItemPostHit;
import com.mmodding.mmodding_lib.library.items.settings.ItemPostMine;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SwordItem.class)
public class SwordItemMixin {

	@Inject(method = "postHit", at = @At("TAIL"))
	private void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker, CallbackInfoReturnable<Boolean> cir) {
		ItemPostHit itemPostHit = AdvancedItemSettings.ITEM_POST_HIT.get((SwordItem) (Object) this);
		if (itemPostHit != null) itemPostHit.apply(stack, target, attacker);
	}

	@Inject(method = "postMine", at = @At("TAIL"))
	private void postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner, CallbackInfoReturnable<Boolean> cir) {
		ItemPostMine itemPostMine = AdvancedItemSettings.ITEM_POST_MINE.get((SwordItem) (Object) this);
		if (itemPostMine != null) itemPostMine.apply(stack, world, state, pos, miner);
	}
}
