package com.mmodding.library.core.api.registry;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

@FunctionalInterface
public interface RegistryLooker {

	<S> HolderGetter<S> lookup(ResourceKey<? extends Registry<? extends S>> key);
}
