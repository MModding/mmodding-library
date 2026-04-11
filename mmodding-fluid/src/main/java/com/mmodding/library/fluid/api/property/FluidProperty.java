package com.mmodding.library.fluid.api.property;

import com.mmodding.library.fluid.impl.FluidPropertyImpl;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;

// These are properties that does not directly deal with player gameplay but that are used by other mods to collect specific information about these fluids
@ApiStatus.NonExtendable
public interface FluidProperty<T> {

	static <T> FluidProperty<T> of(ResourceLocation identifier, T fallback) {
		return new FluidPropertyImpl<>(identifier, fallback);
	}

	ResourceLocation getIdentifier();

	T getFallback();
}
