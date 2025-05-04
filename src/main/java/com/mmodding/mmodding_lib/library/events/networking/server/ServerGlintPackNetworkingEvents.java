package com.mmodding.mmodding_lib.library.events.networking.server;

import com.mmodding.mmodding_lib.library.glint.GlintPackView;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.Item;

import java.util.Map;

@Environment(EnvType.SERVER)
public class ServerGlintPackNetworkingEvents {

	public static final Event<Before> BEFORE = EventFactory.createArrayBacked(Before.class, callbacks -> (item, view) -> {
		for (Before callback : callbacks) {
			callback.beforeGlintPackSent(item, view);
		}
	});

	public static final Event<After> AFTER = EventFactory.createArrayBacked(After.class, callbacks -> (item, view) -> {
		for (After callback : callbacks) {
			callback.afterGlintPackSent(item, view);
		}
	});

	public static final Event<BeforeAll> BEFORE_ALL = EventFactory.createArrayBacked(BeforeAll.class, callbacks -> glintPacks -> {
		for (BeforeAll callback : callbacks) {
			callback.beforeAllGlintPacksSent(glintPacks);
		}
	});

	public static final Event<AfterAll> AFTER_ALL = EventFactory.createArrayBacked(AfterAll.class, callbacks -> glintPacks -> {
		for (AfterAll callback : callbacks) {
			callback.afterAllGlintPacksSent(glintPacks);
		}
	});

	@FunctionalInterface
	@Environment(EnvType.SERVER)
	public interface Before {

		void beforeGlintPackSent(Item item, GlintPackView view);
	}

	@FunctionalInterface
	@Environment(EnvType.SERVER)
	public interface After {

		void afterGlintPackSent(Item item, GlintPackView view);
	}

	@FunctionalInterface
	@Environment(EnvType.SERVER)
	public interface BeforeAll {

		void beforeAllGlintPacksSent(Map<Item, GlintPackView> glintPacks);
	}

	@FunctionalInterface
	@Environment(EnvType.SERVER)
	public interface AfterAll {

		void afterAllGlintPacksSent(Map<Item, GlintPackView> glintPacks);
	}
}
