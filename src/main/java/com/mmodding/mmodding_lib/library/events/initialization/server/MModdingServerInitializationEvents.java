package com.mmodding.mmodding_lib.library.events.initialization.server;

import com.mmodding.mmodding_lib.library.base.AdvancedModContainer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@Environment(EnvType.SERVER)
public class MModdingServerInitializationEvents {

	public static final Event<Start> START = EventFactory.createArrayBacked(Start.class, callbacks -> mod -> {
		for (Start callback : callbacks) {
			callback.onMModdingServerInitializationStart(mod);
		}
	});

	public static final Event<End> END = EventFactory.createArrayBacked(End.class, callbacks -> mod -> {
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
