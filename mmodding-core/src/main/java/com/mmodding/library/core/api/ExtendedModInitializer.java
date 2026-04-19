package com.mmodding.library.core.api;

import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.core.impl.management.ElementsManagerImpl;
import com.mmodding.library.core.impl.management.content.ResourceHandlerContainer;
import com.mmodding.library.core.impl.registry.data.DatagenContainerCallback;
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
		DatagenContainerCallback.EVENT.register(containers -> containers.add(
			ResourceHandlerContainer.createEntrypointContainer(
				advanced.getMetadata().getId(),
				entries -> manager.loadBootstraps(advanced, entries)
			)
		));
		this.onInitialize(advanced);
	}

	void onInitialize(AdvancedContainer mod);
}
