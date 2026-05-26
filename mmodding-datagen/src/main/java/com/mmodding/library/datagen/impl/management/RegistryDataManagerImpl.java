package com.mmodding.library.datagen.impl.management;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.management.content.ResourceProvider;
import com.mmodding.library.datagen.api.management.DataManager;
import com.mmodding.library.datagen.api.management.handler.DataProcessHandler;
import com.mmodding.library.datagen.api.management.handler.FinalDataHandler;
import com.mmodding.library.java.api.container.Pair;
import com.mmodding.library.java.api.list.BiList;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.resources.ResourceKey;

import java.util.Set;
import java.util.function.Predicate;

/**
 * DataManager impl when used by build registry method.
 */
public class RegistryDataManagerImpl implements DataManager {

	private final BiList<ResourceKey<? extends Registry<Object>>, ResourceProvider<Object>> resourceProviders = BiList.create();

	public void provideBootstraps(AdvancedContainer mod, RegistrySetBuilder registries) {
		for (Pair<ResourceKey<? extends Registry<Object>>, ResourceProvider<Object>> entry : this.resourceProviders) {
			ResourceKey<? extends Registry<Object>> registry = entry.first();
			ResourceProvider<Object> provider = entry.second();
			registries.add(registry, context -> provider.configure(mod, context));
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> DataManager resource(ResourceKey<? extends Registry<T>> registry, ResourceProvider<T> provider) {
		this.resourceProviders.add((ResourceKey<? extends Registry<Object>>) registry, (ResourceProvider<Object>) provider);
		return this;
	}

	@Override
	public <T> void task(Class<?> source, FinalDataHandler<T> handler) {}

	@Override
	public <T, P> void task(Class<?> source, DataProcessHandler<T, P> handler, P processor) {}

	@Override
	public <T, P> void task(Class<?> source, DataProcessHandler<T, P> handler, Set<T> selection, P processor) {}

	@Override
	public <T, P> void task(Class<?> source, DataProcessHandler<T, P> handler, Predicate<T> filter, P processor) {}

	@Override
	public <T, P> ChainManager<T, P> chain(Class<?> source, DataProcessHandler<T, P> handler) {
		return new EmptyChainManager<>();
	}

	private static class EmptyChainManager<T, P> implements ChainManager<T, P> {

		@Override
		public ChainManager<T, P> chain(Set<T> selection, P processor) {
			return new EmptyChainManager<>();
		}

		@Override
		public ChainManager<T, P> chain(Predicate<T> filter, P processor) {
			return new EmptyChainManager<>();
		}

		@Override
		public void chain(P processor) {}
	}
}
