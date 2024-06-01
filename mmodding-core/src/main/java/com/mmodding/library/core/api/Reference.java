package com.mmodding.library.core.api;

import com.mmodding.library.core.api.management.content.InjectedContent;
import com.mmodding.library.core.impl.registry.ReferenceFactoryImpl;
import com.mmodding.library.core.impl.registry.ReferenceLiteFactoryImpl;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.function.BiFunction;
import java.util.function.Function;

@InjectedContent({RegistryKey.class, Identifier.class})
public interface Reference<T> {

	Factory VANILLA_FACTORY = Reference.createFactory((namespace, path) -> new Identifier(namespace, path));

	static <T> Reference<T> createId(String namespace, String path) {
		return Reference.VANILLA_FACTORY.createId(namespace, path);
	}

	static Reference.LiteFactory createFactory(Function<String, ? extends Identifier> factory) {
		return new ReferenceLiteFactoryImpl(factory);
	}

	static Reference.Factory createFactory(BiFunction<String, String, ? extends Identifier> factory) {
		return new ReferenceFactoryImpl(factory);
	}

	default RegistryKey<T> provideKey(Registry<T> registry) {
		throw new IllegalStateException();
	}

	default Identifier provideId() {
		throw new IllegalStateException();
	}

	interface LiteFactory {

		<T> Reference<T> createId(String path);
	}

	interface Factory {

		<T> Reference<T> createId(String namespace, String path);
	}
}
