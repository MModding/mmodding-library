package com.mmodding.mmodding_lib.library.utils;

import net.fabricmc.fabric.api.registry.SculkSensorFrequencyRegistry;
import net.minecraft.world.event.GameEvent;

import java.util.Map;

public class GameEventUtils {

	public static void putGameEventInFrequencies(GameEvent gameEvent, int frequency) {
		SculkSensorFrequencyRegistry.register(gameEvent, frequency);
	}

	public static void putGameEventsInFrequencies(Map<? extends GameEvent, ? extends Integer> gameEventIntegerMap) {
		gameEventIntegerMap.forEach(SculkSensorFrequencyRegistry::register);
	}
}
