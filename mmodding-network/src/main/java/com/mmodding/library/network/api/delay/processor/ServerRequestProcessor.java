package com.mmodding.library.network.api.delay.processor;

import com.mmodding.library.java.api.list.MixedList;
import net.minecraft.server.network.ServerPlayerEntity;

@FunctionalInterface
public interface ServerRequestProcessor {

	/**
	 * Processes request arguments sent by the client and returns response arguments.
	 * @param sender the player corresponding to the client that sent the request
	 * @param arguments the request arguments
	 * @return the response arguments
	 */
	MixedList process(ServerPlayerEntity sender, MixedList arguments);
}
