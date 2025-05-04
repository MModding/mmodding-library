package com.mmodding.mmodding_lib.library.base;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.client.MModdingLibClient;
import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.initializers.ClientElementsInitializer;
import net.fabricmc.api.ClientModInitializer;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface MModdingClientModInitializer extends ClientModInitializer {

	@Nullable
	Config getClientConfig();

	List<ClientElementsInitializer> getClientElementsInitializers();

	@Override
	default void onInitializeClient() {
		if (this.getClientConfig() != null) {
			this.getClientConfig().initializeConfig();
			MModdingLibClient.CLIENT_CONFIGS.put(this.getClientConfig().getQualifier(), this.getClientConfig());
		}
		this.getClientElementsInitializers().forEach(ClientElementsInitializer::registerClient);
		this.onInitializeClient(AdvancedModContainer.of(MModdingLib.getModContainer(this.getClass(), "client", ClientModInitializer.class)));
	}

	void onInitializeClient(AdvancedModContainer mod);
}
