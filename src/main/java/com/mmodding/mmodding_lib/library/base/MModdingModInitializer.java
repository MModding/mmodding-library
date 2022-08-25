package com.mmodding.mmodding_lib.library.base;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.initializers.ElementsInitializer;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.List;

public interface MModdingModInitializer extends ModInitializer {

	List<ElementsInitializer> getElementsInitializers();

	@Nullable
	Config getConfig();

	@Override
	@OverridingMethodsMustInvokeSuper
	default void onInitialize(ModContainer mod) {
		MModdingLib.mmoddingMods.add(MModdingModContainer.from(mod));
		this.getElementsInitializers().forEach(ElementsInitializer::register);
		if (this.getConfig() != null) {
			this.getConfig().initializeConfig();
			MModdingLib.configs.put(mod.metadata().id(), this.getConfig());
		}
	}

}
