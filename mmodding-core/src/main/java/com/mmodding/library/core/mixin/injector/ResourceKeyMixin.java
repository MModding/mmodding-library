package com.mmodding.library.core.mixin.injector;

import com.mmodding.library.core.api.registry.extension.ResourceKeyExtension;
import com.mmodding.library.java.api.function.AutoMapper;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ResourceKey.class)
public abstract class ResourceKeyMixin<T> implements ResourceKeyExtension<T> {

	@Shadow
	public abstract Identifier registry();

	@Shadow
	public abstract Identifier identifier();

	@Shadow
	private static <T> ResourceKey<T> create(Identifier identifier, Identifier identifier2) {
		throw new IllegalStateException();
	}

	@Override
	@SuppressWarnings("AddedMixinMembersNamePattern")
	public ResourceKey<T> mapValue(AutoMapper<Identifier> mapper) {
		return create(this.registry(), mapper.map(this.identifier()));
	}
}
