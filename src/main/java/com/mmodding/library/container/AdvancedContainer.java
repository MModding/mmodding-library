package com.mmodding.library.container;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.ModMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

public interface AdvancedContainer extends ModContainer {

	static AdvancedContainer of(ModContainer mod) {

		return new AdvancedContainer() {

			@Override
			public ModMetadata metadata() {
				return mod.metadata();
			}

			@Override
			public Path rootPath() {
				return mod.rootPath();
			}

			@Override
			public List<List<Path>> getSourcePaths() {
				return mod.getSourcePaths();
			}

			@Override
			public BasicSourceType getSourceType() {
				return mod.getSourceType();
			}

			@Override
			public ClassLoader getClassLoader() {
				return mod.getClassLoader();
			}

			@Override
			public Path getPath(String file) {
				return mod.getPath(file);
			}
		};
	}

	default Logger logger() {
		return LoggerFactory.getLogger(this.metadata().name());
	}

	default Identifier createId(String path) {
		return new Identifier(this.metadata().id(), path);
	}

	default <T> RegistryLinkedContainerExecutor<T> withRegistry(Registry<T> registry) {

		return () -> new RegistryLinkedContainer<>() {

			@Override
			public Registry<T> getRegistry() {
				return registry;
			}

			@Override
			public AdvancedContainer getContainer() {
				return AdvancedContainer.this;
			}
		};
	}

	interface RegistryLinkedContainerExecutor<T> {

		RegistryLinkedContainer<T> getContainer();

		default void execute(Consumer<RegistryLinkedContainer<T>> executor) {
			executor.accept(this.getContainer());
		}
	}
}
