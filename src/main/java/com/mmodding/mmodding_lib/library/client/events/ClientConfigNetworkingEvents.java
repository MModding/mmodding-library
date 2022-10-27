package com.mmodding.mmodding_lib.library.client.events;

import com.mmodding.mmodding_lib.library.config.Config;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.event.Event;

@ClientOnly
public class ClientConfigNetworkingEvents {

	public static final Event<Before> BEFORE = Event.create(Before.class, callbacks -> config -> {
		for (Before callback : callbacks) {
			callback.beforeConfigReceived(config);
		}
	});

	public static final Event<After> AFTER = Event.create(After.class, callbacks -> config -> {
		for (After callback : callbacks) {
			callback.afterConfigReceived(config);
		}
	});

	@ClientOnly
	@FunctionalInterface
	public interface Before {

		void beforeConfigReceived(Config config);
	}

	@ClientOnly
	@FunctionalInterface
	public interface After {

		void afterConfigReceived(Config config);
	}
}
