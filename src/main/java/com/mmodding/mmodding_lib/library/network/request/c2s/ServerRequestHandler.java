package com.mmodding.mmodding_lib.library.network.request.c2s;

import com.mmodding.mmodding_lib.library.network.support.NetworkSupport;
import com.mmodding.mmodding_lib.library.network.support.type.NetworkList;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;

@FunctionalInterface
public interface ServerRequestHandler<T extends NetworkSupport> {

	Map<Identifier, ServerRequestHandler<?>> HANDLERS = new HashMap<>();

	@ApiStatus.NonExtendable
	static <T extends NetworkSupport> ServerRequestHandler<T> create(Identifier identifier, ServerRequestHandler<T> handler) {
		HANDLERS.put(identifier, handler);
		return handler;
	}

	T respond(NetworkList arguments);
}
