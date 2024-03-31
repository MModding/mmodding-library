package com.mmodding.library.core.api.registry.pushable;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public interface RegistryPushable<I> {

	Registry<I> getRegistry();

	Identifier getIdentifier();
}
