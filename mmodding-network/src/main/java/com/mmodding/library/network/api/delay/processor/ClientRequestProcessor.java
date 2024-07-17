package com.mmodding.library.network.api.delay.processor;

import com.mmodding.library.java.api.list.MixedList;

@FunctionalInterface
public interface ClientRequestProcessor {

	/**
	 * Processes request arguments sent by the server and returns response arguments.
	 * @param arguments the request arguments
	 * @return the response arguments
	 */
	MixedList process(MixedList arguments);
}
