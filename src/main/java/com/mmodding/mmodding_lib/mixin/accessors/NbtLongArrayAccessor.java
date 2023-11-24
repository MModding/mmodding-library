package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.nbt.NbtLongArray;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(NbtLongArray.class)
public interface NbtLongArrayAccessor {

	@Invoker("toArray")
	static long[] invokeToArray(List<Long> list) {
		throw new AssertionError();
	}
}
