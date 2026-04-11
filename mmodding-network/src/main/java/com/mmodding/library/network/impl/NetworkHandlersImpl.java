package com.mmodding.library.network.impl;

import com.mmodding.library.java.api.map.BiMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class NetworkHandlersImpl {

	public static final Map<ResourceLocation, Class<?>> TYPES = new Object2ObjectOpenHashMap<>();

	public static final Map<Class<?>, ResourceLocation> IDS = new Object2ObjectOpenHashMap<>();

	public static final BiMap<ResourceLocation, FriendlyByteBuf.Reader<?>, FriendlyByteBuf.Writer<?>> HANDLERS = BiMap.create();

	public static <T> void register(Class<T> type, ResourceLocation identifier, FriendlyByteBuf.Reader<T> reader, FriendlyByteBuf.Writer<T> writer) {
		NetworkHandlersImpl.TYPES.put(identifier, type);
		NetworkHandlersImpl.IDS.put(type, identifier);
		NetworkHandlersImpl.HANDLERS.put(identifier, reader, writer);
	}
}
