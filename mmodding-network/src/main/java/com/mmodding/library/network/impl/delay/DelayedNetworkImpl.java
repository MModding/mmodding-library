package com.mmodding.library.network.impl.delay;

import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.java.api.list.MixedList;
import com.mmodding.library.network.api.delay.action.DelayedClientAction;
import com.mmodding.library.network.api.delay.action.DelayedServerAction;
import com.mmodding.library.network.api.delay.processor.ClientRequestProcessor;
import com.mmodding.library.network.api.delay.processor.ServerRequestProcessor;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.UUID;

public class DelayedNetworkImpl {

	public static final Map<UUID, DelayedClientAction> CLIENT_DELAYED_ACTIONS = new Object2ObjectOpenHashMap<>();

	public static final Map<UUID, DelayedServerAction> SERVER_DELAYED_ACTIONS = new Object2ObjectOpenHashMap<>();

	public static final LiteRegistry<ClientRequestProcessor> CLIENT_REQUEST_PROCESSORS = LiteRegistry.create();

	public static final LiteRegistry<ServerRequestProcessor> SERVER_REQUEST_PROCESSORS = LiteRegistry.create();

	public static void registerClientRequestProcessor(Identifier requestIdentifier, ClientRequestProcessor requestProcessor) {
		DelayedNetworkImpl.CLIENT_REQUEST_PROCESSORS.register(requestIdentifier, requestProcessor);
	}

	public static void registerServerRequestProcessor(Identifier requestIdentifier, ServerRequestProcessor requestProcessor) {
		DelayedNetworkImpl.SERVER_REQUEST_PROCESSORS.register(requestIdentifier, requestProcessor);
	}

	@Environment(EnvType.CLIENT)
	public static void executeC2S(Identifier requestIdentifier, MixedList requestArguments, DelayedClientAction action) {
		UUID tracker;
		do {
			tracker = UUID.randomUUID();
		}
		while (DelayedNetworkImpl.CLIENT_DELAYED_ACTIONS.containsKey(tracker));
		DelayedNetworkImpl.CLIENT_DELAYED_ACTIONS.put(tracker, action);
		ClientPlayNetworking.send(new DelayedNetworkPackets.RequestPacket(tracker, requestIdentifier, requestArguments));
	}

	public static void executeS2C(ServerPlayerEntity requestTarget, Identifier requestIdentifier, MixedList requestArguments, DelayedServerAction action) {
		UUID tracker;
		do {
			tracker = UUID.randomUUID();
		}
		while (DelayedNetworkImpl.SERVER_DELAYED_ACTIONS.containsKey(tracker));
		DelayedNetworkImpl.SERVER_DELAYED_ACTIONS.put(tracker, action);
		ServerPlayNetworking.send(requestTarget, new DelayedNetworkPackets.RequestPacket(tracker, requestIdentifier, requestArguments));
	}
}
