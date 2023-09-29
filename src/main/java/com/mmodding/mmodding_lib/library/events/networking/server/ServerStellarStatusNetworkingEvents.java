package com.mmodding.mmodding_lib.library.events.networking.server;

import com.mmodding.mmodding_lib.library.stellar.StellarStatus;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.DedicatedServerOnly;
import org.quiltmc.qsl.base.api.event.Event;

import java.util.Map;

@DedicatedServerOnly
public class ServerStellarStatusNetworkingEvents {

	public static final Event<Before> BEFORE = Event.create(Before.class, callbacks -> (identifier, status) -> {
		for (Before callback : callbacks) {
			callback.beforeStellarStatusSent(identifier, status);
		}
	});

	public static final Event<After> AFTER = Event.create(After.class, callbacks -> (identifier, status) -> {
		for (After callback : callbacks) {
			callback.afterStellarStatusSent(identifier, status);
		}
	});

	public static final Event<BeforeAll> BEFORE_ALL = Event.create(BeforeAll.class, callbacks -> stellarStatus -> {
		for (BeforeAll callback : callbacks) {
			callback.beforeAllStellarStatusSent(stellarStatus);
		}
	});

	public static final Event<AfterAll> AFTER_ALL = Event.create(AfterAll.class, callbacks -> stellarStatus -> {
		for (AfterAll callback : callbacks) {
			callback.afterAllStellarStatusSent(stellarStatus);
		}
	});

	@DedicatedServerOnly
	@FunctionalInterface
	public interface Before {

		void beforeStellarStatusSent(Identifier identifier, StellarStatus status);
	}

	@DedicatedServerOnly
	@FunctionalInterface
	public interface After {

		void afterStellarStatusSent(Identifier identifier, StellarStatus status);
	}

	@DedicatedServerOnly
	@FunctionalInterface
	public interface BeforeAll {

		void beforeAllStellarStatusSent(Map<Identifier, StellarStatus> stellarStatus);
	}

	@DedicatedServerOnly
	@FunctionalInterface
	public interface AfterAll {

		void afterAllStellarStatusSent(Map<Identifier, StellarStatus> stellarStatus);
	}
}
