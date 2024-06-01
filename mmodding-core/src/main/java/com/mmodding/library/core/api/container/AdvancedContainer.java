package com.mmodding.library.core.api.container;

import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.core.impl.container.AdvancedContainerImpl;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.registry.Registry;
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

	default <T> LiteRegistryLinkedContainerExecutor<T> withRegistry(LiteRegistry<T> registry) {

		return () -> new LiteRegistryLinkedContainer<>() {

			@Override
			public LiteRegistry<T> getRegistry() {
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

	interface LiteRegistryLinkedContainerExecutor<T> {

		LiteRegistryLinkedContainer<T> getContainer();

		default void execute(Consumer<LiteRegistryLinkedContainer<T>> executor) {
			executor.accept(this.getContainer());
		}
	}
}
