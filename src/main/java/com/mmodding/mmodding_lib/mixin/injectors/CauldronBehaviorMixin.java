package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.fluids.cauldrons.CauldronBehaviorMap;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(CauldronBehavior.class)
public interface CauldronBehaviorMixin {

	@Inject(method = "registerBucketBehavior", at = @At("TAIL"))
	private static void registerBucketBehavior(Map<Item, CauldronBehavior> behavior, CallbackInfo ci) {
		CauldronBehaviorMap.FILL_BEHAVIORS.forEach((itemSupplier, cauldronBehavior) -> behavior.put(itemSupplier.get(), cauldronBehavior));
	}
}
