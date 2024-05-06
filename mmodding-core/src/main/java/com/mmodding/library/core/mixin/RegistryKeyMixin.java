package com.mmodding.library.core.mixin;

import com.mmodding.library.core.api.Reference;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RegistryKey.class)
public abstract class RegistryKeyMixin<T> implements Reference<T> {

	@Shadow
	public abstract Identifier getValue();

	@Override
	@SuppressWarnings("unchecked")
	public RegistryKey<T> provideKey(Registry<T> registry) {
		return (RegistryKey<T>) (Object) this;
	}

	@Override
	public Identifier provideId() {
		return this.getValue();
	}
}
