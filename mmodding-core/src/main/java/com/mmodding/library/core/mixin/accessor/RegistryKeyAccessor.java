package com.mmodding.library.core.mixin.accessor;

import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RegistryKey.class)
public interface RegistryKeyAccessor {

	@Invoker("of")
	static <T> RegistryKey<T> mmodding$of(Identifier registry, Identifier value) {
		throw new IllegalStateException();
	}
}
