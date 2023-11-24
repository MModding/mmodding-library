package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.nbt.NbtIntArray;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(NbtIntArray.class)
public interface NbtIntArrayAccessor {

	@Invoker("toArray")
	static int[] invokeToArray(List<Integer> list) {
		throw new AssertionError();
	}
}
