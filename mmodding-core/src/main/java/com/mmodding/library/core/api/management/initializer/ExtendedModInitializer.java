package com.mmodding.library.core.api.management.initializer;

import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.core.impl.MModdingInitializer;
import com.mmodding.library.core.api.management.ElementsManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.ModContainer;

public interface ExtendedModInitializer extends ModInitializer {

	static ElementsManager getManager(String modId) {
		return MModdingInitializer.MANAGERS.get(modId);
	}

	void setupManager(ElementsManager.Builder manager);

	@Override
	default void onInitialize() {
		ModContainer mod = MModdingLibrary.getModContainer(this.getClass());
		ElementsManager.Builder builder = ElementsManager.Builder.common();
		this.setupManager(builder);
		ElementsManager manager = builder.build();
		MModdingLibrary.getAllManagers().put(mod.getMetadata().getId(), manager);
		AdvancedContainer advanced = AdvancedContainer.of(mod);
		manager.initDefaultContent(advanced);
		this.onInitialize(advanced);
	}

	void onInitialize(AdvancedContainer mod);
}
