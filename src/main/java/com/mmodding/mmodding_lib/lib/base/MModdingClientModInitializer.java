package com.mmodding.mmodding_lib.lib.base;

import com.mmodding.mmodding_lib.lib.initializers.ClientElementsInitializer;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.List;

public interface MModdingClientModInitializer extends ClientModInitializer {

	List<ClientElementsInitializer> getClientElementsInitializers();

	@Override
	@OverridingMethodsMustInvokeSuper
	default void onInitializeClient(ModContainer mod) {
		for (ClientElementsInitializer clientElementsInitializer: this.getClientElementsInitializers()) clientElementsInitializer.registerClient();
	}

}
