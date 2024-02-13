package com.mmodding.library.mixin;

import com.mmodding.library.core.Reference;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RegistryKey.class)
public class RegistryKeyMixin<T> implements Reference<T> {

	@Override
	@SuppressWarnings("unchecked")
	public RegistryKey<T> provideKey(Registry<T> registry) {
		return (RegistryKey<T>) (Object) this;
	}
}
