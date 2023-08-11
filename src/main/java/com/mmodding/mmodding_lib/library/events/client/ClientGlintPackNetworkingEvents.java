package com.mmodding.mmodding_lib.library.events.client;

import com.mmodding.mmodding_lib.library.glint.client.GlintPack;
import net.minecraft.item.Item;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.event.Event;

import java.util.Map;

@ClientOnly
public class ClientGlintPackNetworkingEvents {

	public static final Event<Before> BEFORE = Event.create(Before.class, callbacks -> glintPacks -> {
		for (Before callback : callbacks) {
			callback.beforeGlintPackReceived(glintPacks);
		}
	});

	public static final Event<After> AFTER = Event.create(After.class, callbacks -> glintPacks -> {
		for (After callback : callbacks) {
			callback.afterGlintPackReceived(glintPacks);
		}
	});

	@ClientOnly
	@FunctionalInterface
	public interface Before {

		void beforeGlintPackReceived(Map<Item, GlintPack> glintPacks);
	}

	@ClientOnly
	@FunctionalInterface
	public interface After {

		void afterGlintPackReceived(Map<Item, GlintPack> glintPacks);
	}
}
