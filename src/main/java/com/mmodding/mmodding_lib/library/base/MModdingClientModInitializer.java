package com.mmodding.mmodding_lib.library.base;

import com.mmodding.mmodding_lib.client.MModdingLibClient;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.initializers.ClientElementsInitializer;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import java.util.List;

public interface MModdingClientModInitializer extends ClientModInitializer {

	List<ClientElementsInitializer> getClientElementsInitializers();

	@Nullable
	Config getClientConfig();

	@Override
	@MustBeInvokedByOverriders
	default void onInitializeClient(ModContainer mod) {
		this.getClientElementsInitializers().forEach(ClientElementsInitializer::registerClient);
		if (this.getClientConfig() != null) {
			this.getClientConfig().initializeConfig();
			MModdingLibClient.CLIENT_CONFIGS.put(this.getClientConfig().getConfigName(), this.getClientConfig());
		}
	}

}
