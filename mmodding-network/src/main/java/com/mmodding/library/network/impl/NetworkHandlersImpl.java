package com.mmodding.library.network.impl;

import com.mmodding.library.java.api.map.BiMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.Map;

public class NetworkHandlersImpl {

	public static final Map<Identifier, Class<?>> TYPES = new Object2ObjectOpenHashMap<>();

	public static final Map<Class<?>, Identifier> IDS = new Object2ObjectOpenHashMap<>();

	public static final BiMap<Identifier, PacketByteBuf.PacketReader<?>, PacketByteBuf.PacketWriter<?>> HANDLERS = BiMap.create();

	public static <T> void register(Class<T> type, Identifier identifier, PacketByteBuf.PacketReader<T> reader, PacketByteBuf.PacketWriter<T> writer) {
		NetworkHandlersImpl.TYPES.put(identifier, type);
		NetworkHandlersImpl.IDS.put(type, identifier);
		NetworkHandlersImpl.HANDLERS.put(identifier, reader, writer);
	}
}
