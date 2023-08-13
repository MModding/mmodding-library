package com.mmodding.mmodding_lib.library.base;

import com.mmodding.mmodding_lib.client.MModdingLibClient;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.initializers.ClientElementsInitializer;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import java.util.List;

public interface MModdingClientModInitializer extends ClientModInitializer {

	@Nullable
	Config getClientConfig();

	List<ClientElementsInitializer> getClientElementsInitializers();

	@Override
	default void onInitializeClient(ModContainer mod) {
		if (this.getClientConfig() != null) {
			this.getClientConfig().initializeConfig();
			MModdingLibClient.CLIENT_CONFIGS.put(this.getClientConfig().getQualifier(), this.getClientConfig());
		}
		this.getClientElementsInitializers().forEach(ClientElementsInitializer::registerClient);
		this.onInitializeClient(AdvancedModContainer.of(mod));
	}

	void onInitializeClient(AdvancedModContainer mod);
}
