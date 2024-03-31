package com.mmodding.library.registry.api.content;

import com.mmodding.library.container.api.AdvancedContainer;
import com.mmodding.library.registry.api.ContentHolderProvider;
import net.minecraft.registry.Registry;

public interface DoubleContentHolder<L, R> extends ContentHolder {

	void register(Registry<L> leftRegistry, Registry<R> rightRegistry, AdvancedContainer mod);

	interface Provider<L, R> extends ContentHolderProvider {

		DoubleContentHolder<L, R> init(Registry<L> leftRegistry, Registry<R> rightRegistry, AdvancedContainer mod);
	}
}
