package com.mmodding.mmodding_lib.library.base;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.initializers.ElementsInitializer;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import java.util.List;

public interface MModdingModInitializer extends ModInitializer {

	List<ElementsInitializer> getElementsInitializers();

	@Nullable
	Config getConfig();

	@Override
	@MustBeInvokedByOverriders
	default void onInitialize(ModContainer mod) {
		MModdingLib.MMODDING_MODS.add(MModdingModContainer.from(mod));
		if (this.getConfig() != null) {
			this.getConfig().initializeConfig();
			MModdingLib.CONFIGS.put(this.getConfig().getQualifier(), this.getConfig());
		}
		this.getElementsInitializers().forEach(ElementsInitializer::register);
	}
}
