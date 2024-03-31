package com.mmodding.library.registry.api;

import net.minecraft.util.Identifier;

public interface LiteRegistryPushable<I> {

	LiteRegistry<I> getRegistry();

	Identifier getIdentifier();
}
