package com.mmodding.mmodding_lib.library.events.initialization.client;

import com.mmodding.mmodding_lib.library.base.AdvancedModContainer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

@Environment(EnvType.CLIENT)
public class MModdingClientInitializationEvents {

	public static final Event<Start> START = EventFactory.createArrayBacked(Start.class, callbacks -> mod -> {
		for (Start callback : callbacks) {
			callback.onMModdingClientInitializationStart(mod);
		}
	});

	public static final Event<End> END = EventFactory.createArrayBacked(End.class, callbacks -> mod -> {
		for (End callback : callbacks) {
			callback.onMModdingClientInitializationEnd(mod);
		}
	});

	@FunctionalInterface
	public interface Start {

		void onMModdingClientInitializationStart(AdvancedModContainer mod);
	}

	@FunctionalInterface
	public interface End {

		void onMModdingClientInitializationEnd(AdvancedModContainer mod);
	}
}
