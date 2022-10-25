package com.mmodding.mmodding_lib.client.events;

import com.mmodding.mmodding_lib.library.config.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.quiltmc.qsl.base.api.event.Event;

@Environment(EnvType.CLIENT)
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

	@Environment(EnvType.CLIENT)
	@FunctionalInterface
	public interface Before {

		void beforeConfigReceived(Config config);
	}

	@Environment(EnvType.CLIENT)
	@FunctionalInterface
	public interface After {

		void afterConfigReceived(Config config);
	}
}
