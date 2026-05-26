package com.mmodding.library.core.api.client;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.core.impl.management.ElementsManagerImpl;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.ModContainer;
import org.jetbrains.annotations.ApiStatus;

/**
 * @see com.mmodding.library.core.api.ExtendedModInitializer
 * @see ClientModInitializer
 */
public interface ExtendedClientModInitializer extends ClientModInitializer {

	@Override
	@ApiStatus.Internal
	default void onInitializeClient() {
		ModContainer mod = MModdingLibrary.getModContainer(this.getClass());
		ElementsManagerImpl manager = new ElementsManagerImpl();
		this.setupManager(manager);
		AdvancedContainer advanced = AdvancedContainer.of(mod);
		manager.loadElements(advanced);
		this.onInitializeClient(advanced);
	}

	void setupManager(ElementsManager manager);

	void onInitializeClient(AdvancedContainer mod);
}
