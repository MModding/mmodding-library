package com.mmodding.library.core.mixin.injector;

import com.mmodding.library.core.api.registry.extension.RegistryKeyExtension;
import com.mmodding.library.java.api.function.AutoMapper;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RegistryKey.class)
public abstract class RegistryKeyMixin<T> implements RegistryKeyExtension<T> {

	@Shadow
	public abstract Identifier getRegistry();

	@Shadow
	public abstract Identifier getValue();

	@Shadow
	private static <T> RegistryKey<T> of(Identifier identifier, Identifier identifier2) {
		throw new IllegalStateException();
	}

	@Override
	@SuppressWarnings("AddedMixinMembersNamePattern")
	public RegistryKey<T> mapValue(AutoMapper<Identifier> mapper) {
		return of(this.getRegistry(), mapper.map(this.getValue()));
	}
}
