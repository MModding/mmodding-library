package com.mmodding.library.initializer;

import com.mmodding.library.MModdingExperiments;
import com.mmodding.library.container.api.AdvancedContainer;
import com.mmodding.library.registry.ElementsManager;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public interface ExtendedModInitializer extends ModInitializer {

	static ElementsManager getManager(String modId) {
		return MModdingExperiments.MANAGERS.get(modId);
	}

	void setupManager(ElementsManager.Builder manager);

	@Override
	default void onInitialize(ModContainer mod) {
		ElementsManager.Builder builder = ElementsManager.Builder.common();
		this.setupManager(builder);
		ElementsManager manager = builder.build();
		MModdingExperiments.MANAGERS.put(mod.metadata().id(), manager);
		AdvancedContainer advanced = AdvancedContainer.of(mod);
		manager.initDefaultContent(advanced);
		this.onInitialize(advanced);
	}

	void onInitialize(AdvancedContainer mod);
}
