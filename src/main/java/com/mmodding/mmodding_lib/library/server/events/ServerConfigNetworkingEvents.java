package com.mmodding.mmodding_lib.library.server.events;

import com.mmodding.mmodding_lib.library.config.Config;
import org.quiltmc.loader.api.minecraft.DedicatedServerOnly;
import org.quiltmc.qsl.base.api.event.Event;

@DedicatedServerOnly
public class ServerConfigNetworkingEvents {

	public static final Event<Before> BEFORE = Event.create(Before.class, callbacks -> config -> {
		for (Before callback : callbacks) {
			callback.beforeConfigSent(config);
		}
	});

	public static final Event<After> AFTER = Event.create(After.class, callbacks -> config -> {
		for (After callback : callbacks) {
			callback.afterConfigSent(config);
		}
	});

	@DedicatedServerOnly
	@FunctionalInterface
	public interface Before {

		void beforeConfigSent(Config config);
	}

	@DedicatedServerOnly
	@FunctionalInterface
	public interface After {

		void afterConfigSent(Config config);
	}
}
