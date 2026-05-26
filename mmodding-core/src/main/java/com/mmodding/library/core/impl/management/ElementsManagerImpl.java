package com.mmodding.library.core.impl.management;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.core.api.management.content.ContentProvider;
import net.fabricmc.loader.api.FabricLoader;

import java.util.ArrayList;
import java.util.List;

public class ElementsManagerImpl implements ElementsManager {

	private final List<ContentProvider> contentProviders = new ArrayList<>();

	public void loadElements(AdvancedContainer mod) {
		this.contentProviders.forEach(provider -> provider.register(mod));
	}

	@Override
	public ElementsManagerImpl content(ContentProvider provider) {
		this.contentProviders.add(provider);
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
