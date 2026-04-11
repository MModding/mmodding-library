package com.mmodding.library.core.mixin.injector;

import com.mmodding.library.core.api.registry.extension.ResourceKeyExtension;
import com.mmodding.library.java.api.function.AutoMapper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ResourceKey.class)
public abstract class ResourceKeyMixin<T> implements ResourceKeyExtension<T> {

	@Shadow
	public abstract ResourceLocation registry();

	@Shadow
	public abstract ResourceLocation location();

	@Shadow
	private static <T> ResourceKey<T> create(ResourceLocation identifier, ResourceLocation identifier2) {
		throw new IllegalStateException();
	}

	@Override
	@SuppressWarnings("AddedMixinMembersNamePattern")
	public ResourceKey<T> mapValue(AutoMapper<ResourceLocation> mapper) {
		return create(this.registry(), mapper.map(this.location()));
	}
}
