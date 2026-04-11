package com.mmodding.library.core.mixin.accessor;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ResourceKey.class)
public interface RegistryKeyAccessor {

	@Invoker("create")
	static <T> ResourceKey<T> mmodding$of(ResourceLocation registry, ResourceLocation value) {
		throw new IllegalStateException();
	}
}
