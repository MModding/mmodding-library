package com.mmodding.library.network.api.delay;

import com.mmodding.library.java.api.list.MixedList;
import com.mmodding.library.network.api.delay.action.DelayedClientAction;
import com.mmodding.library.network.api.delay.action.DelayedServerAction;
import com.mmodding.library.network.api.delay.processor.ClientRequestProcessor;
import com.mmodding.library.network.api.delay.processor.ServerRequestProcessor;
import com.mmodding.library.network.impl.delay.DelayedNetworkImpl;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class DelayedNetwork {

	/**
	 * Registers a {@link ClientRequestProcessor} under the specified request {@link Identifier}.
	 * @param requestIdentifier the request {@link Identifier}
	 * @param requestProcessor the {@link ClientRequestProcessor}
	 */
	@Environment(EnvType.CLIENT)
	public static void registerClientRequestProcessor(Identifier requestIdentifier, ClientRequestProcessor requestProcessor) {
		DelayedNetworkImpl.registerClientRequestProcessor(requestIdentifier, requestProcessor);
	}

	/**
	 * Registers a {@link ServerRequestProcessor} under the specified request {@link Identifier}.
	 * @param requestIdentifier the request {@link Identifier}
	 * @param requestProcessor the {@link ServerRequestProcessor}
	 */
	public static void registerServerRequestProcessor(Identifier requestIdentifier, ServerRequestProcessor requestProcessor) {
		DelayedNetworkImpl.registerServerRequestProcessor(requestIdentifier, requestProcessor);
	}

	/**
	 * Makes a request to the server, to then execute some actions with the data
	 * included in the response arguments.
	 * @param requestIdentifier the request identifier, which allows the server to know how to properly process it
	 * @param requestArguments the request arguments, to provide some data first to the server if necessary
	 * @param action the delayed action waiting for the server's response
	 */
	@Environment(EnvType.CLIENT)
	public static void executeC2S(Identifier requestIdentifier, MixedList requestArguments, DelayedClientAction action) {
		DelayedNetworkImpl.executeC2S(requestIdentifier, requestArguments, action);
	}

	/**
	 * Makes a request to one client, to then execute some actions with the data
	 * included in the response arguments.
	 * @param requestTarget the player of the targeted client
	 * @param requestIdentifier the request identifier, which allows the client to know how to properly process it
	 * @param requestArguments the request arguments, to provide some data first to the client if necessary
	 * @param action the delayed action waiting for the client's response
	 */
	public static void executeS2C(ServerPlayerEntity requestTarget, Identifier requestIdentifier, MixedList requestArguments, DelayedServerAction action) {
		DelayedNetworkImpl.executeS2C(requestTarget, requestIdentifier, requestArguments, action);
	}
}
