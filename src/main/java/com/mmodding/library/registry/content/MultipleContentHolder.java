package com.mmodding.library.registry.content;

import com.mmodding.library.container.AdvancedContainer;
import com.mmodding.library.registry.RegistrableProvider;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

import java.util.Map;

public interface MultipleContentHolder extends ContentHolder {

	void register(Map<RegistryKey<? extends Registry<?>>, Registry<?>> registries, AdvancedContainer mod);

	interface Provider extends RegistrableProvider {

		MultipleContentHolder init(Map<RegistryKey<? extends Registry<?>>, Registry<?>> registries, AdvancedContainer mod);
	}
}
