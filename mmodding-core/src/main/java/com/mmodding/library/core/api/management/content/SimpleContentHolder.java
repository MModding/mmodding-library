package com.mmodding.library.core.api.management.content;

import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.management.ContentHolderProvider;
import net.minecraft.registry.Registry;

public interface SimpleContentHolder<T> extends ContentHolder {

	void register(Registry<T> registry, AdvancedContainer mod);

	interface Provider<T> extends ContentHolderProvider {

		SimpleContentHolder<T> init(Registry<T> registry, AdvancedContainer mod);
	}
}
