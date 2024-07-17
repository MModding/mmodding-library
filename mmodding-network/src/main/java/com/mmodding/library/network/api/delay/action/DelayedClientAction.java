package com.mmodding.library.network.api.delay.action;

import com.mmodding.library.java.api.list.MixedList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
@FunctionalInterface
public interface DelayedClientAction {

	/**
	 * Handles the delayed action with the obtained arguments from the server.
	 * @param arguments the obtained arguments from the server
	 */
	void handle(MixedList arguments);
}
