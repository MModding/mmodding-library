package com.mmodding.library.core.api.management.content;

import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.management.ContentHolderProvider;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

import java.util.Map;

public interface MultipleContentHolder extends ContentHolder {

	void register(Map<RegistryKey<? extends Registry<?>>, Registry<?>> registries, AdvancedContainer mod);

	interface Provider extends ContentHolderProvider {

		MultipleContentHolder init(Map<RegistryKey<? extends Registry<?>>, Registry<?>> registries, AdvancedContainer mod);
	}
}
