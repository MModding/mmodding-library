package com.mmodding.library.core.api.container;

import com.mmodding.library.core.api.registry.pushable.LiteRegistryPushable;
import com.mmodding.library.core.api.registry.LiteRegistry;
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
