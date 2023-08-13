package com.mmodding.mmodding_lib.library.events.client;

import com.mmodding.mmodding_lib.library.base.AdvancedModContainer;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.event.Event;

@ClientOnly
public class MModdingClientInitializationEvents {

	public static final Event<Start> START = Event.create(Start.class, callbacks -> mod -> {
		for (Start callback: callbacks) {
			callback.onMModdingClientInitializationStart(mod);
		}
	});

	public static final Event<End> END = Event.create(End.class, callbacks -> mod -> {
		for (End callback: callbacks) {
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
