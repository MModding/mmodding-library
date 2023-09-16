package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.mmodding_lib.library.fluids.buckets.CustomBucketItem;
import com.mmodding.mmodding_lib.library.utils.Self;
import com.mmodding.mmodding_lib.mixin.accessors.FluidBlockAccessor;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BucketItem.class)
public class BucketItemMixin implements Self<BucketItem> {

	@WrapOperation(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/FluidDrainable;tryDrainFluid(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Lnet/minecraft/item/ItemStack;"))
	private ItemStack use(FluidDrainable drainable, WorldAccess world, BlockPos pos, BlockState state, Operation<ItemStack> original) {
		if (this.getObject() instanceof CustomBucketItem bucket) {
			ItemStack check = ItemStack.EMPTY;
			if (drainable instanceof FluidBlock block) {
				check = bucket.getManager().getFilledItem(new ItemStack(((FluidBlockAccessor) block).getFluid().getBucketItem()));
			}
			else if (drainable instanceof BubbleColumnBlock || drainable instanceof Waterloggable) {
				check = bucket.getManager().getFilledItem(new ItemStack(Items.WATER_BUCKET));
			}
			else if (drainable instanceof PowderSnowBlock) {
				check = bucket.getManager().getFilledItem(new ItemStack(Items.POWDER_SNOW_BUCKET));
			}
			return !check.isEmpty()
				? bucket.getManager().getFilledItem(original.call(drainable, world, pos, state))
				: this.defaultResult(bucket, drainable, world, pos, state, original);
		}
		return original.call(drainable, world, pos, state);
	}

	@WrapOperation(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/BucketItem;getEmptiedStack(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;)Lnet/minecraft/item/ItemStack;"))
	private ItemStack use(ItemStack stack, PlayerEntity player, Operation<ItemStack> original) {
		return this.getObject() instanceof CustomBucketItem bucket ? bucket.getRemainderStack(stack, player) : original.call(stack, player);
	}

	@Unique
	private ItemStack defaultResult(CustomBucketItem bucket, FluidDrainable drainable, WorldAccess world, BlockPos pos, BlockState state, Operation<ItemStack> original) {
		return bucket.getManager().safeMode() ? original.call(drainable, world, pos, state) : ItemStack.EMPTY;
	}
}
