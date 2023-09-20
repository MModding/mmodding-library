package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mmodding.mmodding_lib.library.fluids.buckets.CustomBucketItem;
import com.mmodding.mmodding_lib.library.fluids.cauldrons.CauldronBehaviorMap;
import com.mmodding.mmodding_lib.library.fluids.cauldrons.VanillaCauldronBehaviors;
import net.minecraft.block.BlockState;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(CauldronBehavior.class)
public interface CauldronBehaviorMixin {

	@Inject(method = "registerBehavior", at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", shift = At.Shift.AFTER, ordinal = 0))
	private static void registerEmptyBehavior(CallbackInfo ci) {
		CauldronBehavior.EMPTY_CAULDRON_BEHAVIOR.putAll(VanillaCauldronBehaviors.EMPTY_BEHAVIOR);
	}

	@Inject(method = "registerBehavior", at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", shift = At.Shift.AFTER, ordinal = 40))
	private static void registerWaterBehavior(CallbackInfo ci) {
		CauldronBehavior.WATER_CAULDRON_BEHAVIOR.putAll(VanillaCauldronBehaviors.WATER_BEHAVIOR);
	}

	@Inject(method = "registerBehavior", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/cauldron/CauldronBehavior;registerBucketBehavior(Ljava/util/Map;)V", shift = At.Shift.AFTER, ordinal = 2))
	private static void registerLavaBehavior(CallbackInfo ci) {
		CauldronBehavior.LAVA_CAULDRON_BEHAVIOR.putAll(VanillaCauldronBehaviors.LAVA_BEHAVIOR);
	}

	@Inject(method = "registerBehavior", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/cauldron/CauldronBehavior;registerBucketBehavior(Ljava/util/Map;)V", shift = At.Shift.AFTER, ordinal = 3))
	private static void registerSnowPowderBehavior(CallbackInfo ci) {
		CauldronBehavior.POWDER_SNOW_CAULDRON_BEHAVIOR.putAll(VanillaCauldronBehaviors.SNOW_POWDER_BEHAVIOR);
	}

	@Inject(method = "registerBucketBehavior", at = @At("TAIL"))
	private static void registerBucketBehavior(Map<Item, CauldronBehavior> behavior, CallbackInfo ci) {
		CauldronBehaviorMap.FILL_BEHAVIORS.forEach(behavior::put);
	}

	@ModifyExpressionValue(method = "fillCauldron", at = @At(value = "FIELD", target = "Lnet/minecraft/item/Items;BUCKET:Lnet/minecraft/item/Item;", opcode = Opcodes.GETSTATIC))
	private static Item fillCauldron(Item original, World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack, BlockState state, SoundEvent soundEvent) {
		return stack.getItem() instanceof CustomBucketItem bucket ? bucket.getManager().getEmptiedItemOrDefault(stack).getItem() : stack.getItem();
	}
}
