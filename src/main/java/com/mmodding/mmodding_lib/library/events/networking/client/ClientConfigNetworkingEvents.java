package com.mmodding.mmodding_lib.library.events.networking.client;

import com.mmodding.mmodding_lib.library.config.StaticConfig;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.event.Event;

import java.util.Map;

@ClientOnly
public class ClientConfigNetworkingEvents {

	public static final Event<Before> BEFORE = Event.create(Before.class, callbacks -> configs -> {
		for (Before callback : callbacks) {
			callback.beforeConfigReceived(configs);
		}
	});

	public static final Event<After> AFTER = Event.create(After.class, callbacks -> configs -> {
		for (After callback : callbacks) {
			callback.afterConfigReceived(configs);
		}
	});

	@ClientOnly
	@FunctionalInterface
	public interface Before {

		void beforeConfigReceived(Map<String, StaticConfig> configs);
	}

	@ClientOnly
	@FunctionalInterface
	public interface After {

		void afterConfigReceived(Map<String, StaticConfig> configs);
	}
}
