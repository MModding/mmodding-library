package com.mmodding.mmodding_lib.library.sounds.client.music;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.MusicSound;

@Environment(EnvType.CLIENT)
public interface MusicTypeSelectionCallback {

	/**
	 * Allows modifying the selected {@link MusicSound} at the return of {@link MinecraftClient#getMusicType()}.
	 */
	Event<MusicTypeSelector> EVENT = EventFactory.createArrayBacked(MusicTypeSelector.class, callbacks -> (client, original) -> {
		MusicSound selected = original;
		for (MusicTypeSelector callback : callbacks) {
			selected = callback.select(client, original);
		}
		return selected;
	});

	@FunctionalInterface
	@Environment(EnvType.CLIENT)
	interface MusicTypeSelector {

		/**
		 * Modifies the selected {@link MusicSound} at the return of {@link MinecraftClient#getMusicType()}.
		 * @param client the minecraft client
		 * @param original the originally selected music type
		 * @return the newly selected music type
		 */
		MusicSound select(MinecraftClient client, MusicSound original);
	}
}
