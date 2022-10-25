package com.mmodding.mmodding_lib.server.events;

import com.mmodding.mmodding_lib.library.config.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.quiltmc.qsl.base.api.event.Event;

@Environment(EnvType.SERVER)
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

	@Environment(EnvType.SERVER)
	@FunctionalInterface
	public interface Before {

		void beforeConfigSent(Config config);
	}

	@Environment(EnvType.SERVER)
	@FunctionalInterface
	public interface After {

		void afterConfigSent(Config config);
	}
}
