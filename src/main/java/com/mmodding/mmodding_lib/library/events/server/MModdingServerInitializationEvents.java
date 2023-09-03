package com.mmodding.mmodding_lib.library.events.server;

import com.mmodding.mmodding_lib.library.base.AdvancedModContainer;
import org.quiltmc.loader.api.minecraft.DedicatedServerOnly;
import org.quiltmc.qsl.base.api.event.Event;

@DedicatedServerOnly
public class MModdingServerInitializationEvents {

	public static final Event<Start> START = Event.create(Start.class, callbacks -> mod -> {
		for (Start callback : callbacks) {
			callback.onMModdingServerInitializationStart(mod);
		}
	});

	public static final Event<End> END = Event.create(End.class, callbacks -> mod -> {
		for (End callback : callbacks) {
			callback.onMModdingServerInitializationEnd(mod);
		}
	});

	@FunctionalInterface
	public interface Start {

		void onMModdingServerInitializationStart(AdvancedModContainer mod);
	}

	@FunctionalInterface
	public interface End {

		void onMModdingServerInitializationEnd(AdvancedModContainer mod);
	}
}
