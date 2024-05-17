package com.mmodding.library.core.api.management.context;

import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.management.content.ContentHolder;
import com.mmodding.library.core.api.management.content.DoubleContentHolder;
import com.mmodding.library.core.api.management.content.MultipleContentHolder;
import com.mmodding.library.core.api.management.content.SimpleContentHolder;
import com.mmodding.library.core.api.management.ContentHolderProvider;
import com.mmodding.library.java.api.BiList;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

import java.util.HashMap;
import java.util.Map;
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
	public <T1, T2> ContentHolder transform(AdvancedContainer mod, Map<RegistryKey<? extends Registry<?>>, Registry<?>> registries, ContentHolderProvider provider) {
		BiList<RegistryKey<? extends Registry<?>>, Registry<?>> requiredRegistries = this.retainRequiredRegistries(registries);
		if (this.requiredKeys.length == 0) {
			throw new IllegalStateException("Context should contain at least one required key");
		}
		else if (this.requiredKeys.length == 1) {
			return ((SimpleContentHolder.Provider<T1>) provider).init((Registry<T1>) requiredRegistries.getSecond(0), mod);
		}
		else if (this.requiredKeys.length == 2) {
			return ((DoubleContentHolder.Provider<T1, T2>) provider).init((Registry<T1>) requiredRegistries.getSecond(0), (Registry<T2>) requiredRegistries.getSecond(1), mod);
		}
		else {
			return ((MultipleContentHolder.Provider) provider).init(this.pairListToMap(requiredRegistries), mod);
		}
	}

	// should keep an eye on that weirdo

	@SuppressWarnings("unchecked")
	public <T1, T2> void transfer(AdvancedContainer mod, Map<RegistryKey<? extends Registry<?>>, Registry<?>> registries, ContentHolder holder) {
		BiList<RegistryKey<? extends Registry<?>>, Registry<?>> requiredRegistries = this.retainRequiredRegistries(registries);
		if (this.requiredKeys.length == 0) {
			throw new IllegalStateException("Context should contain at least one required key");
		}
		else if (this.requiredKeys.length == 1) {
			((SimpleContentHolder<T1>) holder).register((Registry<T1>) requiredRegistries.getSecond(0), mod);
		}
		else if (this.requiredKeys.length == 2) {
			((DoubleContentHolder<T1, T2>) holder).register((Registry<T1>) requiredRegistries.getSecond(0), (Registry<T2>) requiredRegistries.getSecond(1), mod);
		}
		else {
			((MultipleContentHolder) holder).register(this.pairListToMap(requiredRegistries), mod);
		}
	}

	private BiList<RegistryKey<? extends Registry<?>>, Registry<?>> retainRequiredRegistries(Map<RegistryKey<? extends Registry<?>>, Registry<?>> registries) {
		BiList<RegistryKey<? extends Registry<?>>, Registry<?>> requiredRegistries = BiList.create();
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
			.forEach(entry -> requiredRegistries.add(entry.getKey(), entry.getValue()));
		return requiredRegistries;
	}

	private Map<RegistryKey<? extends Registry<?>>, Registry<?>> pairListToMap(BiList<RegistryKey<? extends Registry<?>>, Registry<?>> content) {
		Map<RegistryKey<? extends Registry<?>>, Registry<?>> map = new HashMap<>();
		content.forEach(map::put);
		return map;
	}
}
