package com.mmodding.library.registry.api.context;

import com.mmodding.library.container.api.AdvancedContainer;
import com.mmodding.library.registry.api.RegistrableProvider;
import com.mmodding.library.registry.api.content.DoubleContentHolder;
import com.mmodding.library.registry.api.content.MultipleContentHolder;
import com.mmodding.library.registry.api.content.ContentHolder;
import com.mmodding.library.registry.api.content.SimpleContentHolder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;


public abstract class RegistryContext {

	private final RegistryKey<? extends Registry<?>>[] requiredKeys;

	@SafeVarargs
	protected RegistryContext(RegistryKey<? extends Registry<?>>... requiredKeys) {
		this.requiredKeys = requiredKeys;
	}

	public RegistryKey<? extends Registry<?>>[] getRequiredKeys() {
		return this.requiredKeys;
	}

	// should keep an eye on that weirdo

	@SuppressWarnings("unchecked")
	public <T1, T2> ContentHolder transform(AdvancedContainer mod, Map<RegistryKey<? extends Registry<?>>, Registry<?>> registries, RegistrableProvider provider) {
		List<Pair<RegistryKey<? extends Registry<?>>, Registry<?>>> requiredRegistries = this.retainRequiredRegistries(registries);
		if (this.requiredKeys.length == 0) {
			throw new IllegalStateException("Context should contain at least one required key");
		}
		else if (this.requiredKeys.length == 1) {
			return ((SimpleContentHolder.Provider<T1>) provider).init((Registry<T1>) requiredRegistries.get(0).getValue(), mod);
		}
		else if (this.requiredKeys.length == 2) {
			return ((DoubleContentHolder.Provider<T1, T2>) provider).init((Registry<T1>) requiredRegistries.get(0).getValue(), (Registry<T2>) requiredRegistries.get(1).getValue(), mod);
		}
		else {
			return ((MultipleContentHolder.Provider) provider).init(this.pairListToMap(requiredRegistries), mod);
		}
	}

	// should keep an eye on that weirdo

	@SuppressWarnings("unchecked")
	public <T1, T2> void transfer(AdvancedContainer mod, Map<RegistryKey<? extends Registry<?>>, Registry<?>> registries, ContentHolder holder) {
		List<Pair<RegistryKey<? extends Registry<?>>, Registry<?>>> requiredRegistries = this.retainRequiredRegistries(registries);
		if (this.requiredKeys.length == 0) {
			throw new IllegalStateException("Context should contain at least one required key");
		}
		else if (this.requiredKeys.length == 1) {
			((SimpleContentHolder<T1>) holder).register((Registry<T1>) requiredRegistries.get(0).getValue(), mod);
		}
		else if (this.requiredKeys.length == 2) {
			((DoubleContentHolder<T1, T2>) holder).register((Registry<T1>) requiredRegistries.get(0).getValue(), (Registry<T2>) requiredRegistries.get(1).getValue(), mod);
		}
		else {
			((MultipleContentHolder) holder).register(this.pairListToMap(requiredRegistries), mod);
		}
	}

	private List<Pair<RegistryKey<? extends Registry<?>>, Registry<?>>> retainRequiredRegistries(Map<RegistryKey<? extends Registry<?>>, Registry<?>> registries) {
		List<Pair<RegistryKey<? extends Registry<?>>, Registry<?>>> requiredRegistries = new ArrayList<>();
		registries.entrySet().stream()
			.filter(
				entry -> {
					RegistryKey<? extends Registry<?>> registryKey = entry.getKey();
					AtomicBoolean passes = new AtomicBoolean();
					for (RegistryKey<? extends Registry<?>> current : this.requiredKeys) {
						boolean registryMatches = registryKey.getRegistry() == current.getRegistry();
						boolean valueMatches = registryKey.getValue() == current.getValue();
						if (registryMatches && valueMatches) {
							passes.set(true);
						}
					}
					return passes.get();
				}
			)
			.forEach(entry -> requiredRegistries.add(Pair.of(entry.getKey(), entry.getValue())));
		return requiredRegistries;
	}

	private Map<RegistryKey<? extends Registry<?>>, Registry<?>> pairListToMap(List<Pair<RegistryKey<? extends Registry<?>>, Registry<?>>> content) {
		Map<RegistryKey<? extends Registry<?>>, Registry<?>> map = new HashMap<>();
		content.forEach(pair -> map.put(pair.getKey(), pair.getValue()));
		return map;
	}
}
