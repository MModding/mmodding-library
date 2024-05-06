package com.mmodding.library.core.mixin;

import com.mmodding.library.core.api.Reference;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Identifier.class)
public class IdentifierMixin implements Reference<Object> {

	@Override
	public RegistryKey<Object> provideKey(Registry<Object> registry) {
		return RegistryKey.of(registry.getKey(), (Identifier) (Object) this);
	}

	@Override
	public Identifier provideId() {
		return (Identifier) (Object) this;
	}
}
