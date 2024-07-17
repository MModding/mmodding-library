package com.mmodding.library.network.api.delay.processor;

import com.mmodding.library.java.api.list.MixedList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
@FunctionalInterface
public interface ClientRequestProcessor {

	/**
	 * Processes request arguments sent by the server and returns response arguments.
	 * @param arguments the request arguments
	 * @return the response arguments
	 */
	MixedList process(MixedList arguments);
}
