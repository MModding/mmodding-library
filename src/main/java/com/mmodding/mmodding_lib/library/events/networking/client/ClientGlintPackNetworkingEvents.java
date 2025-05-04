package com.mmodding.mmodding_lib.library.events.networking.client;

import com.mmodding.mmodding_lib.library.glint.GlintPackView;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.Item;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class ClientGlintPackNetworkingEvents {

	public static final Event<Before> BEFORE = EventFactory.createArrayBacked(Before.class, callbacks -> glintPacks -> {
		for (Before callback : callbacks) {
			callback.beforeGlintPackReceived(glintPacks);
		}
	});

	public static final Event<After> AFTER = EventFactory.createArrayBacked(After.class, callbacks -> glintPacks -> {
		for (After callback : callbacks) {
			callback.afterGlintPackReceived(glintPacks);
		}
	});

	@FunctionalInterface
	@Environment(EnvType.CLIENT)
	public interface Before {

		void beforeGlintPackReceived(Map<Item, GlintPackView> glintPacks);
	}

	@FunctionalInterface
	@Environment(EnvType.CLIENT)
	public interface After {

		void afterGlintPackReceived(Map<Item, GlintPackView> glintPacks);
	}
}
