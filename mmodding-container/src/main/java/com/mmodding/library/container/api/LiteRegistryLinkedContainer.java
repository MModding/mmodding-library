package com.mmodding.library.container.api;

import com.mmodding.library.registry.api.LiteRegistry;
import com.mmodding.library.registry.api.LiteRegistryPushable;
import net.minecraft.util.Identifier;

public interface LiteRegistryLinkedContainer<I> {

	LiteRegistry<I> getRegistry();

	AdvancedContainer getContainer();

	default LiteRegistryPushable<I> createId(String path) {

		return new LiteRegistryPushable<>() {

			@Override
			public LiteRegistry<I> getRegistry() {
				return LiteRegistryLinkedContainer.this.getRegistry();
			}

			@Override
			public Identifier getIdentifier() {
				return LiteRegistryLinkedContainer.this.getContainer().createId(path);
			}
		};
	}
}
