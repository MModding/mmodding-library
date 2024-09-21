package com.mmodding.library.network.api.delay.action;

import com.mmodding.library.java.api.list.MixedList;
import net.minecraft.server.network.ServerPlayerEntity;

@FunctionalInterface
public interface DelayedServerAction {

	/**
	 * Handles the delayed action with the obtained arguments from the client.
	 * @param target the player corresponding to the client where the arguments are coming from
	 * @param arguments the obtained arguments from the client
	 */
	void handle(ServerPlayerEntity target, MixedList arguments);
}
