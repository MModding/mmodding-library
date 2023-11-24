package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.nbt.NbtByteArray;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(NbtByteArray.class)
public interface NbtByteArrayAccessor {

	@Invoker("toArray")
	static byte[] invokeToArray(List<Byte> list) {
		throw new AssertionError();
	}
}
