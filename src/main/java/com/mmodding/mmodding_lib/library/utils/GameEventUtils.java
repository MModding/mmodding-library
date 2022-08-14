package com.mmodding.mmodding_lib.library.utils;

import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.SculkSensorBlock;
import net.minecraft.util.Util;
import net.minecraft.world.event.GameEvent;

import java.util.Map;

public class GameEventUtils {

	public static void putGameEventInFrequencies(GameEvent gameEvent, int frequency) {
		SculkSensorBlock.FREQUENCIES = Object2IntMaps.unmodifiable(Util.make(new Object2IntOpenHashMap<>(), map -> {
			map.putAll(SculkSensorBlock.FREQUENCIES);
			map.put(gameEvent, frequency);
		}));
	}

	public static void putGameEventsInFrequencies(Map<? extends GameEvent, ? extends Integer> gameEventIntegerMap) {
		SculkSensorBlock.FREQUENCIES = Object2IntMaps.unmodifiable(Util.make(new Object2IntOpenHashMap<>(), map -> {
			map.putAll(SculkSensorBlock.FREQUENCIES);
			map.putAll(gameEventIntegerMap);
		}));
	}
}
