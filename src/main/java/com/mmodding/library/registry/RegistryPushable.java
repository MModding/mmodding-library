package com.mmodding.library.registry;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public interface RegistryPushable<I> {

	Registry<I> getRegistry();

	Identifier getIdentifier();
}
