package com.mmodding.mmodding_lib.library.base;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.client.MModdingLibClient;
import com.mmodding.mmodding_lib.library.client.TemporaryConfig;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.initializers.ElementsInitializer;
import net.fabricmc.api.EnvType;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.minecraft.MinecraftQuiltLoader;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import java.util.List;

public interface MModdingModInitializer extends ModInitializer {

	List<ElementsInitializer> getElementsInitializers();

	@Nullable
	Config getConfig();

	@Override
	@MustBeInvokedByOverriders
	default void onInitialize(ModContainer mod) {
		MModdingLib.mmoddingMods.add(MModdingModContainer.from(mod));
		this.getElementsInitializers().forEach(ElementsInitializer::register);
		if (this.getConfig() != null) {
			this.getConfig().initializeConfig();
			MModdingLib.configs.put(this.getConfig().getConfigName(), this.getConfig());
			if (MinecraftQuiltLoader.getEnvironmentType() == EnvType.CLIENT) {
				MModdingLibClient.clientConfigs.put(this.getConfig().getConfigName(), TemporaryConfig.fromConfig(this.getConfig()));
			}
		}
	}
}
