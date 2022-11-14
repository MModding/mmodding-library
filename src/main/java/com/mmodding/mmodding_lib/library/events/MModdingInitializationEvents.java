package com.mmodding.mmodding_lib.library.events;

import com.mmodding.mmodding_lib.library.base.MModdingModContainer;
import org.quiltmc.qsl.base.api.event.Event;

public class MModdingInitializationEvents {

	public static final Event<Start> START = Event.create(Start.class, callbacks -> mmoddingContainer -> {
		for (Start callback: callbacks) {
			callback.onMModdingInitializationStart(mmoddingContainer);
		}
	});

	public static final Event<End> END = Event.create(End.class, callbacks -> mmoddingContainer -> {
		for (End callback: callbacks) {
			callback.onMModdingInitializationEnd(mmoddingContainer);
		}
	});

	@FunctionalInterface
	public interface Start {

		void onMModdingInitializationStart(MModdingModContainer mmoddingContainer);
	}

	@FunctionalInterface
	public interface End {

		void onMModdingInitializationEnd(MModdingModContainer mmoddingContainer);
	}
}
