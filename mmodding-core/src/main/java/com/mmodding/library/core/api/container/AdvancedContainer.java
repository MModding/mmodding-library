package com.mmodding.library.core.api.container;

import com.mmodding.library.core.api.registry.factory.RegistryKeyFactory;
import com.mmodding.library.core.impl.container.AdvancedContainerImpl;
import com.mmodding.library.core.impl.registry.factory.RegistryKeyFactoryImpl;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public interface AdvancedContainer extends ModContainer {

	static AdvancedContainer of(ModContainer mod) {
		return new AdvancedContainerImpl(mod);
	}

	default Logger logger() {
		return LoggerFactory.getLogger(this.getMetadata().getName());
	}

	default Identifier createId(String path) {
		return new Identifier(this.getMetadata().getId(), path);
	}

	default <T> RegistryKey<T> createKey(RegistryKey<? extends Registry<T>> registry, String path) {
		return RegistryKey.of(registry, this.createId(path));
	}

	default <T> void withRegistry(Registry<T> registry, Consumer<RegistryKeyFactory<T>> consumer) {
		this.withRegistry(registry.getKey(), consumer);
	}

	default <T> void withRegistry(RegistryKey<? extends Registry<T>> registry, Consumer<RegistryKeyFactory<T>> consumer) {
		consumer.accept(new RegistryKeyFactoryImpl<>(registry, this.getMetadata().getId()));
	}
}
