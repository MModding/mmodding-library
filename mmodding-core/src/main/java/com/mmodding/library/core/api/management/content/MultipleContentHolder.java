package com.mmodding.library.core.api.management.content;

import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.management.ContentHolderProvider;
import net.minecraft.registry.Registry;

import java.util.List;

public interface MultipleContentHolder extends ContentHolder {

	void register(List<Registry<?>> registries, AdvancedContainer mod);

	interface Provider extends ContentHolderProvider {

		MultipleContentHolder init(List<Registry<?>> registries, AdvancedContainer mod);
	}
}
