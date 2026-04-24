package com.mmodding.library.core.impl.management.content;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.management.content.ResourceProvider;
import com.mmodding.library.java.api.container.Pair;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.JsonKeySortOrderCallback;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.Nullable;

public class ResourceGenerator implements DataGeneratorEntrypoint {

	private final AdvancedContainer mod;
	private final BiList<ResourceKey<? extends Registry<Object>>, ResourceProvider<Object>> providers;
	private final DataGeneratorEntrypoint wrapped;

	public ResourceGenerator(AdvancedContainer mod, BiList<ResourceKey<? extends Registry<Object>>, ResourceProvider<Object>> providers, @Nullable DataGeneratorEntrypoint wrapped) {
		this.mod = mod;
		this.providers = providers;
		this.wrapped = wrapped;
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registries) {
		for (Pair<ResourceKey<? extends Registry<Object>>, ResourceProvider<Object>> entry : this.providers) {
			ResourceKey<? extends Registry<Object>> registry = entry.first();
			ResourceProvider<Object> provider = entry.second();
			registries.add(registry, context -> provider.configure(this.mod, context));
		}
		if (this.wrapped != null) {
			this.wrapped.buildRegistry(registries);
		}
	}

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();
		pack.addProvider((output, future) -> new FabricDynamicRegistryProvider(output, future) {

			@Override
			protected void configure(HolderLookup.Provider registries, Entries entries) {
				ResourceGenerator.this.providers.forEachFirst(key -> entries.addAll(entries.getLookups().lookupOrThrow(key)));
			}

			@Override
			public String getName() {
				return "Internal Resource Handler";
			}
		});
		if (this.wrapped != null) {
			this.wrapped.onInitializeDataGenerator(generator);
		}
	}

	@Override
	public String getEffectiveModId() {
		return this.wrapped != null ? this.wrapped.getEffectiveModId() : DataGeneratorEntrypoint.super.getEffectiveModId();
	}

	@Override
	public void addJsonKeySortOrders(JsonKeySortOrderCallback callback) {
		if (this.wrapped != null) this.addJsonKeySortOrders(callback);
	}
}
