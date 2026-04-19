package com.mmodding.library.core.impl.management;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.core.api.management.content.ResourceProvider;
import com.mmodding.library.core.api.management.content.ContentProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.loader.api.FabricLoader;

import java.util.ArrayList;
import java.util.List;

public class ElementsManagerImpl implements ElementsManager {

	private final List<ContentProvider> contentProviders = new ArrayList<>();
	private final List<ResourceProvider> resourceProviders = new ArrayList<>();

	public void loadElements(AdvancedContainer mod) {
		this.contentProviders.forEach(provider -> provider.register(mod));
	}

	public void loadBootstraps(AdvancedContainer mod, FabricDynamicRegistryProvider.Entries registrable) {
		this.resourceProviders.forEach(provider -> provider.run(mod, registrable));
	}

	@Override
	public ElementsManagerImpl content(ContentProvider provider) {
		this.contentProviders.add(provider);
		return this;
	}

	@Override
	public ElementsManagerImpl resource(ResourceProvider provider) {
		if (System.getProperty("fabric-api.datagen") != null) {
			this.resourceProviders.add(provider);
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
