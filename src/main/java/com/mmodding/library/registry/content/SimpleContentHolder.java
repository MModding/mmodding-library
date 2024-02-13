package com.mmodding.library.registry.content;

import com.mmodding.library.container.api.AdvancedContainer;
import com.mmodding.library.registry.RegistrableProvider;
import net.minecraft.registry.Registry;

public interface SimpleContentHolder<T> extends ContentHolder {

	void register(Registry<T> registry, AdvancedContainer mod);

	interface Provider<T> extends RegistrableProvider {

		SimpleContentHolder<T> init(Registry<T> registry, AdvancedContainer mod);
	}
}
