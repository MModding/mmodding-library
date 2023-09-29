package com.mmodding.mmodding_lib.library.events.networking.server;

import com.mmodding.mmodding_lib.library.glint.GlintPackView;
import net.minecraft.item.Item;
import org.quiltmc.loader.api.minecraft.DedicatedServerOnly;
import org.quiltmc.qsl.base.api.event.Event;

import java.util.Map;

@DedicatedServerOnly
public class ServerGlintPackNetworkingEvents {

	public static final Event<Before> BEFORE = Event.create(Before.class, callbacks -> (item, view) -> {
		for (Before callback : callbacks) {
			callback.beforeGlintPackSent(item, view);
		}
	});

	public static final Event<After> AFTER = Event.create(After.class, callbacks -> (item, view) -> {
		for (After callback : callbacks) {
			callback.afterGlintPackSent(item, view);
		}
	});

	public static final Event<BeforeAll> BEFORE_ALL = Event.create(BeforeAll.class, callbacks -> glintPacks -> {
		for (BeforeAll callback : callbacks) {
			callback.beforeAllGlintPacksSent(glintPacks);
		}
	});

	public static final Event<AfterAll> AFTER_ALL = Event.create(AfterAll.class, callbacks -> glintPacks -> {
		for (AfterAll callback : callbacks) {
			callback.afterAllGlintPacksSent(glintPacks);
		}
	});

	@DedicatedServerOnly
	@FunctionalInterface
	public interface Before {

		void beforeGlintPackSent(Item item, GlintPackView view);
	}

	@DedicatedServerOnly
	@FunctionalInterface
	public interface After {

		void afterGlintPackSent(Item item, GlintPackView view);
	}

	@DedicatedServerOnly
	@FunctionalInterface
	public interface BeforeAll {

		void beforeAllGlintPacksSent(Map<Item, GlintPackView> glintPacks);
	}

	@DedicatedServerOnly
	@FunctionalInterface
	public interface AfterAll {

		void afterAllGlintPacksSent(Map<Item, GlintPackView> glintPacks);
	}
}
