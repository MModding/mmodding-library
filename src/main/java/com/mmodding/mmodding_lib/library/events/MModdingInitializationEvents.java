package com.mmodding.mmodding_lib.library.events;

import com.mmodding.mmodding_lib.library.base.AdvancedModContainer;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class MModdingInitializationEvents {

	public static final Event<Start> START = EventFactory.createArrayBacked(Start.class, callbacks -> mod -> {
		for (Start callback : callbacks) {
			callback.onMModdingInitializationStart(mod);
		}
	});

	public static final Event<End> END = EventFactory.createArrayBacked(End.class, callbacks -> mod -> {
		for (End callback : callbacks) {
			callback.onMModdingInitializationEnd(mod);
		}
	});

	@FunctionalInterface
	public interface Start {

		void onMModdingInitializationStart(AdvancedModContainer mod);
	}

	@FunctionalInterface
	public interface End {

		void onMModdingInitializationEnd(AdvancedModContainer mod);
	}
}
