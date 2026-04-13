package com.mmodding.library.network.impl;

import com.mmodding.library.java.api.map.BiMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamDecoder;
import net.minecraft.network.codec.StreamEncoder;
import net.minecraft.resources.Identifier;

public class NetworkHandlersImpl {

	public static final Map<Identifier, Class<?>> TYPES = new Object2ObjectOpenHashMap<>();

	public static final Map<Class<?>, Identifier> IDS = new Object2ObjectOpenHashMap<>();

	public static final BiMap<Identifier, StreamDecoder<? extends FriendlyByteBuf, ?>, StreamEncoder<? extends FriendlyByteBuf, ?>> HANDLERS = BiMap.create();

	public static <T> void register(Class<T> type, Identifier identifier, StreamDecoder<? extends FriendlyByteBuf, T> reader, StreamEncoder<? extends FriendlyByteBuf, T> writer) {
		NetworkHandlersImpl.TYPES.put(identifier, type);
		NetworkHandlersImpl.IDS.put(type, identifier);
		NetworkHandlersImpl.HANDLERS.put(identifier, reader, writer);
	}
}
