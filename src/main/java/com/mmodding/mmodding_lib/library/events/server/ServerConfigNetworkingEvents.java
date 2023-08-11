package com.mmodding.mmodding_lib.library.events.server;

import com.mmodding.mmodding_lib.library.config.Config;
import org.quiltmc.loader.api.minecraft.DedicatedServerOnly;
import org.quiltmc.qsl.base.api.event.Event;

import java.util.Map;

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

	public static final Event<BeforeAll> BEFORE_ALL = Event.create(BeforeAll.class, callbacks -> configs -> {
		for (BeforeAll callback : callbacks) {
			callback.beforeAllConfigsSent(configs);
		}
	});

	public static final Event<AfterAll> AFTER_ALL = Event.create(AfterAll.class, callbacks -> configs -> {
		for (AfterAll callback : callbacks) {
			callback.afterAllConfigsSent(configs);
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

	@DedicatedServerOnly
	@FunctionalInterface
	public interface BeforeAll {

		void beforeAllConfigsSent(Map<String, Config> configs);
	}

	@DedicatedServerOnly
	@FunctionalInterface
	public interface AfterAll {

		void afterAllConfigsSent(Map<String, Config> configs);
	}
}
