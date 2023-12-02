package com.mmodding.mmodding_lib.library.network.request.s2c;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import com.mmodding.mmodding_lib.library.network.support.type.NetworkList;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;

@FunctionalInterface
public interface ClientRequestHandler<T extends NetworkSupport> {

	Map<Identifier, ClientRequestHandler<?>> HANDLERS = new HashMap<>();

	@ApiStatus.NonExtendable
	static <T extends NetworkSupport> ClientRequestHandler<T> create(Identifier identifier, ClientRequestHandler<T> handler) {
		HANDLERS.put(identifier, handler);
		return handler;
	}

	T respond(ClientPlayerEntity player, NetworkList arguments);
}
