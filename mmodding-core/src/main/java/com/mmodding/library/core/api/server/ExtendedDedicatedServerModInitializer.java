package com.mmodding.library.core.api.server;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.core.impl.management.ElementsManagerImpl;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.loader.api.ModContainer;
import org.jetbrains.annotations.ApiStatus;

/**
 * @see com.mmodding.library.core.api.ExtendedModInitializer
 * @see DedicatedServerModInitializer
 */
public interface ExtendedDedicatedServerModInitializer extends DedicatedServerModInitializer {

	@Override
	@ApiStatus.Internal
	default void onInitializeServer() {
		ModContainer mod = MModdingLibrary.getModContainer(this.getClass());
		ElementsManagerImpl manager = new ElementsManagerImpl();
		this.setupManager(manager);
		AdvancedContainer advanced = AdvancedContainer.of(mod);
		manager.loadElements(advanced);
		this.onInitializeServer(advanced);
	}

	void setupManager(ElementsManager manager);

	void onInitializeServer(AdvancedContainer mod);
}
