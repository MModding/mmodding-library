package com.mmodding.library.core.api.registry.pushable;

import com.mmodding.library.core.api.registry.LiteRegistry;
import net.minecraft.util.Identifier;

public interface LiteRegistryPushable<I> {

	LiteRegistry<I> getRegistry();

	Identifier getIdentifier();
}
