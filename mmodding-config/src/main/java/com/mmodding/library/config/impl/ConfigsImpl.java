package com.mmodding.library.config.impl;

import com.mmodding.library.config.api.Config;
import com.mmodding.library.config.api.element.ConfigElementTypeWrapper;
import com.mmodding.library.config.api.element.builtin.FloatingRange;
import com.mmodding.library.config.api.element.builtin.IntegerRange;
import com.mmodding.library.config.impl.element.builtin.ColorWrapper;
import com.mmodding.library.config.impl.element.builtin.FloatingRangeWrapper;
import com.mmodding.library.config.impl.element.builtin.IntegerRangeWrapper;
import com.mmodding.library.java.api.color.Color;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.util.Identifier;

import java.util.Map;

public class ConfigsImpl {

	static final Map<Identifier, Config> CONFIGS = new Object2ObjectOpenHashMap<>();

	public static final Object2ObjectOpenHashMap<Class<?>, ConfigElementTypeWrapper<?, ?, ? extends ConfigElementTypeWrapper.Properties>> WRAPPERS = new Object2ObjectOpenHashMap<>();

	public static Map<Identifier, Config> getAll() {
		return new Object2ObjectOpenHashMap<>(ConfigsImpl.CONFIGS);
	}

	public static Config get(Identifier identifier) {
		return ConfigsImpl.CONFIGS.get(identifier);
	}

	public static <T, V, P extends ConfigElementTypeWrapper.Properties> void registerWrapper(Class<T> type, ConfigElementTypeWrapper<T, V, P> wrapper) {
		ConfigsImpl.WRAPPERS.put(type, wrapper);
	}

	static {
		ConfigsImpl.registerWrapper(Color.class, new ColorWrapper());
		ConfigsImpl.registerWrapper(IntegerRange.class, new IntegerRangeWrapper());
		ConfigsImpl.registerWrapper(FloatingRange.class, new FloatingRangeWrapper());
	}
}
