package com.mmodding.mmodding_lib.library.events.networking.common;

import com.mmodding.mmodding_lib.library.stellar.StellarStatus;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.Identifier;

import java.util.Map;

public class StellarStatusNetworkingEvents {

	public static final Event<Before> BEFORE = EventFactory.createArrayBacked(Before.class, callbacks -> (identifier, status) -> {
		for (Before callback : callbacks) {
			callback.beforeStellarStatusSent(identifier, status);
		}
	});

	public static final Event<After> AFTER = EventFactory.createArrayBacked(After.class, callbacks -> (identifier, status) -> {
		for (After callback : callbacks) {
			callback.afterStellarStatusSent(identifier, status);
		}
	});

	public static final Event<BeforeAll> BEFORE_ALL = EventFactory.createArrayBacked(BeforeAll.class, callbacks -> stellarStatus -> {
		for (BeforeAll callback : callbacks) {
			callback.beforeAllStellarStatusSent(stellarStatus);
		}
	});

	public static final Event<AfterAll> AFTER_ALL = EventFactory.createArrayBacked(AfterAll.class, callbacks -> stellarStatus -> {
		for (AfterAll callback : callbacks) {
			callback.afterAllStellarStatusSent(stellarStatus);
		}
	});

	@FunctionalInterface
	public interface Before {

		void beforeStellarStatusSent(Identifier identifier, StellarStatus status);
	}

	@FunctionalInterface
	public interface After {

		void afterStellarStatusSent(Identifier identifier, StellarStatus status);
	}

	@FunctionalInterface
	public interface BeforeAll {

		void beforeAllStellarStatusSent(Map<Identifier, StellarStatus> stellarStatus);
	}

	@FunctionalInterface
	public interface AfterAll {

		void afterAllStellarStatusSent(Map<Identifier, StellarStatus> stellarStatus);
	}
}
