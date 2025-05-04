package com.mmodding.mmodding_lib.library.events.networking.server;

import com.mmodding.mmodding_lib.library.config.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import java.util.Map;

@Environment(EnvType.SERVER)
public class ServerConfigNetworkingEvents {

	public static final Event<Before> BEFORE = EventFactory.createArrayBacked(Before.class, callbacks -> config -> {
		for (Before callback : callbacks) {
			callback.beforeConfigSent(config);
		}
	});

	public static final Event<After> AFTER = EventFactory.createArrayBacked(After.class, callbacks -> config -> {
		for (After callback : callbacks) {
			callback.afterConfigSent(config);
		}
	});

	public static final Event<BeforeAll> BEFORE_ALL = EventFactory.createArrayBacked(BeforeAll.class, callbacks -> configs -> {
		for (BeforeAll callback : callbacks) {
			callback.beforeAllConfigsSent(configs);
		}
	});

	public static final Event<AfterAll> AFTER_ALL = EventFactory.createArrayBacked(AfterAll.class, callbacks -> configs -> {
		for (AfterAll callback : callbacks) {
			callback.afterAllConfigsSent(configs);
		}
	});

	@FunctionalInterface
	@Environment(EnvType.SERVER)
	public interface Before {

		void beforeConfigSent(Config config);
	}

	@FunctionalInterface
	@Environment(EnvType.SERVER)
	public interface After {

		void afterConfigSent(Config config);
	}

	@FunctionalInterface
	@Environment(EnvType.SERVER)
	public interface BeforeAll {

		void beforeAllConfigsSent(Map<String, Config> configs);
	}

	@FunctionalInterface
	@Environment(EnvType.SERVER)
	public interface AfterAll {

		void afterAllConfigsSent(Map<String, Config> configs);
	}
}
