package com.mmodding.library.core.api;

import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.core.impl.management.ElementsManagerImpl;
import com.mmodding.library.core.impl.management.content.ResourceGeneratorContainer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.ModContainer;
import org.jetbrains.annotations.ApiStatus;

public interface ExtendedModInitializer extends ModInitializer {

	void setupManager(ElementsManager manager);

	@Override
	@ApiStatus.Internal
	default void onInitialize() {
		ModContainer mod = MModdingLibrary.getModContainer(this.getClass());
		ElementsManagerImpl manager = new ElementsManagerImpl();
		this.setupManager(manager);
		AdvancedContainer advanced = AdvancedContainer.of(mod);
		manager.loadElements(advanced);
		ResourceGeneratorContainer.prepareResources(advanced, manager);
		this.onInitialize(advanced);
	}

	void onInitialize(AdvancedContainer mod);
}
