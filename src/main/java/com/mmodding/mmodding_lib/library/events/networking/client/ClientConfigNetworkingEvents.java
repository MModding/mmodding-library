package com.mmodding.mmodding_lib.library.events.networking.client;

import com.mmodding.mmodding_lib.library.config.StaticConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class ClientConfigNetworkingEvents {

	public static final Event<Before> BEFORE = EventFactory.createArrayBacked(Before.class, callbacks -> configs -> {
		for (Before callback : callbacks) {
			callback.beforeConfigReceived(configs);
		}
	});

	public static final Event<After> AFTER = EventFactory.createArrayBacked(After.class, callbacks -> configs -> {
		for (After callback : callbacks) {
			callback.afterConfigReceived(configs);
		}
	});

	@FunctionalInterface
	@Environment(EnvType.CLIENT)
	public interface Before {

		void beforeConfigReceived(Map<String, StaticConfig> configs);
	}

	@FunctionalInterface
	@Environment(EnvType.CLIENT)
	public interface After {

		void afterConfigReceived(Map<String, StaticConfig> configs);
	}
}
