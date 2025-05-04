package com.mmodding.mmodding_lib.library.base;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.initializers.ElementsInitializer;
import net.fabricmc.api.ModInitializer;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface MModdingModInitializer extends ModInitializer {

	@Nullable
	Config getConfig();

	List<ElementsInitializer> getElementsInitializers();

	@Override
	default void onInitialize() {
		if (this.getConfig() != null) {
			this.getConfig().initializeConfig();
			MModdingLib.CONFIGS.put(this.getConfig().getQualifier(), this.getConfig());
		}
		this.getElementsInitializers().forEach(ElementsInitializer::register);
		this.onInitialize(AdvancedModContainer.of(MModdingLib.getModContainer(this.getClass(), "main", ModInitializer.class)));
	}

	void onInitialize(AdvancedModContainer mod);
}
