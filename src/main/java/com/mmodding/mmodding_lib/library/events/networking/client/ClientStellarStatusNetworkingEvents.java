package com.mmodding.mmodding_lib.library.events.networking.client;

import com.mmodding.mmodding_lib.library.stellar.client.ClientStellarStatus;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.event.Event;

@ClientOnly
public class ClientStellarStatusNetworkingEvents {

	public static final Event<Before> BEFORE = Event.create(Before.class, callbacks -> (identifier, status) -> {
		for (Before callback : callbacks) {
			callback.beforeStellarStatusReceived(identifier, status);
		}
	});

	public static final Event<After> AFTER = Event.create(After.class, callbacks -> (identifier, status) -> {
		for (After callback : callbacks) {
			callback.afterStellarStatusReceived(identifier, status);
		}
	});

	@ClientOnly
	@FunctionalInterface
	public interface Before {

		void beforeStellarStatusReceived(Identifier identifier, ClientStellarStatus status);
	}

	@ClientOnly
	@FunctionalInterface
	public interface After {

		void afterStellarStatusReceived(Identifier identifier, ClientStellarStatus status);
	}
}
