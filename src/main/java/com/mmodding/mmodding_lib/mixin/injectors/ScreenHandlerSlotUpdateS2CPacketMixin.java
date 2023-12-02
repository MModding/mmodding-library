package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.mmodding_lib.library.glint.GlintPackView;
import com.mmodding.mmodding_lib.library.items.data.HiddenStackDataInsertionCallback;
import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ScreenHandlerSlotUpdateS2CPacket.class)
public class ScreenHandlerSlotUpdateS2CPacketMixin {

	@WrapOperation(method = "<init>(IIILnet/minecraft/item/ItemStack;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;copy()Lnet/minecraft/item/ItemStack;"))
	private ItemStack init(ItemStack stack, Operation<ItemStack> original) {
		ItemStack copied = original.call(stack);
		if (GlintPackView.of(copied) != null) {
			copied.getHiddenDataStorage().putIdentifier(new MModdingIdentifier("glint_pack"), GlintPackView.of(copied).getGlintPack(copied));
			HiddenStackDataInsertionCallback.EVENT.invoker().insert(copied, copied.getHiddenDataStorage());
		}
		return copied;
	}
}
