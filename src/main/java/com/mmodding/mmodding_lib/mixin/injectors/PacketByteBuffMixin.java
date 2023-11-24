package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.mmodding_lib.ducks.ItemStackDuckInterface;
import com.mmodding.mmodding_lib.library.networking.NetworkSupport;
import com.mmodding.mmodding_lib.library.utils.Self;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PacketByteBuf.class)
public class PacketByteBuffMixin implements Self<PacketByteBuf> {

	@WrapOperation(method = "readItemStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;setNbt(Lnet/minecraft/nbt/NbtCompound;)V", shift = At.Shift.AFTER))
	private void readItemStack(ItemStack itemStack, NbtCompound compound, Operation<Void> original) {
		original.call(itemStack, compound);
		((ItemStackDuckInterface) (Object) itemStack).mmodding_lib$setHiddenStackData(NetworkSupport.readComplete(this.getObject()));
	}

	@WrapOperation(method = "writeItemStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketByteBuf;writeNbt(Lnet/minecraft/nbt/NbtCompound;)Lnet/minecraft/network/PacketByteBuf;", shift = At.Shift.AFTER))
	private PacketByteBuf writeItemStack(PacketByteBuf instance, NbtCompound compound, Operation<PacketByteBuf> original, @Local ItemStack stack) {
		PacketByteBuf buf = original.call(instance, compound);
		((ItemStackDuckInterface) (Object) stack).mmodding_lib$getHiddenStackData().write(instance);
		return buf;
	}
}
