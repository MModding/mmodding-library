package com.mmodding.mmodding_lib.library.base;

import com.mmodding.mmodding_lib.library.config.Config;
import com.mmodding.mmodding_lib.library.initializers.ClientElementsInitializer;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.io.IOException;
import java.util.List;

public interface MModdingClientModInitializer extends ClientModInitializer {

	List<ClientElementsInitializer> getClientElementsInitializers();

	@Nullable
	Config getClientConfig();

	@Override
	@OverridingMethodsMustInvokeSuper
	default void onInitializeClient(ModContainer mod) {
		this.getClientElementsInitializers().forEach(ClientElementsInitializer::registerClient);
		if (this.getClientConfig() != null) {
			try {
				this.getClientConfig().initializeConfig();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
