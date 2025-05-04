package com.mmodding.mmodding_lib.library.events.networking.client;

import com.mmodding.mmodding_lib.library.stellar.StellarStatus;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ClientStellarStatusNetworkingEvents {

	public static final Event<Before> BEFORE = EventFactory.createArrayBacked(Before.class, callbacks -> (identifier, status) -> {
		for (Before callback : callbacks) {
			callback.beforeStellarStatusReceived(identifier, status);
		}
	});

	public static final Event<After> AFTER = EventFactory.createArrayBacked(After.class, callbacks -> (identifier, status) -> {
		for (After callback : callbacks) {
			callback.afterStellarStatusReceived(identifier, status);
		}
	});

	@FunctionalInterface
	@Environment(EnvType.CLIENT)
	public interface Before {

		void beforeStellarStatusReceived(Identifier identifier, StellarStatus status);
	}

	@FunctionalInterface
	@Environment(EnvType.CLIENT)
	public interface After {

		void afterStellarStatusReceived(Identifier identifier, StellarStatus status);
	}
}
