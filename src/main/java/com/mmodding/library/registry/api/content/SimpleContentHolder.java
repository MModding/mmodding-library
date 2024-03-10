package com.mmodding.library.registry.api.content;

import com.mmodding.library.container.api.AdvancedContainer;
import com.mmodding.library.registry.api.ContentHolderProvider;
import net.minecraft.registry.Registry;

public interface SimpleContentHolder<T> extends ContentHolder {

	void register(Registry<T> registry, AdvancedContainer mod);

	interface Provider<T> extends ContentHolderProvider {

		SimpleContentHolder<T> init(Registry<T> registry, AdvancedContainer mod);
	}
}
