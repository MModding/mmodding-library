package com.mmodding.mmodding_lib.library.events;

import com.mmodding.mmodding_lib.library.base.AdvancedModContainer;
import org.quiltmc.qsl.base.api.event.Event;

public class MModdingInitializationEvents {

	public static final Event<Start> START = Event.create(Start.class, callbacks -> mod -> {
		for (Start callback : callbacks) {
			callback.onMModdingInitializationStart(mod);
		}
	});

	public static final Event<End> END = Event.create(End.class, callbacks -> mod -> {
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
