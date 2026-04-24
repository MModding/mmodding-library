package com.mmodding.library.core.impl.management;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.core.api.management.content.ResourceProvider;
import com.mmodding.library.core.api.management.content.ContentProvider;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.ArrayList;
import java.util.List;

public class ElementsManagerImpl implements ElementsManager {

	private final List<ContentProvider> contentProviders = new ArrayList<>();
	private final BiList<ResourceKey<? extends Registry<Object>>, ResourceProvider<Object>> resourceProviders = BiList.create();

	public void loadElements(AdvancedContainer mod) {
		this.contentProviders.forEach(provider -> provider.register(mod));
	}

	public BiList<ResourceKey<? extends Registry<Object>>, ResourceProvider<Object>> getBootstraps() {
		return this.resourceProviders;
	}

	@Override
	public ElementsManagerImpl content(ContentProvider provider) {
		this.contentProviders.add(provider);
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> ElementsManagerImpl resource(ResourceKey<? extends Registry<T>> registry, ResourceProvider<T> provider) {
		if (System.getProperty("fabric-api.datagen") != null) {
			this.resourceProviders.add((ResourceKey<? extends Registry<Object>>) registry, (ResourceProvider<Object>) provider);
		}
		return this;
	}

	@Override
	public ElementsManagerImpl ifModLoaded(String modId, ContentProvider provider) {
		if (FabricLoader.getInstance().isModLoaded(modId)) {
			this.content(provider);
		}
		return this;
	}
}
