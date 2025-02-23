package com.mmodding.mmodding_lib.library.sounds.client.music;

import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.MusicSound;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.event.Event;

@ClientOnly
public interface MusicTypeSelectionCallback {

	/**
	 * Allows modifying the selected {@link MusicSound} at the return of {@link MinecraftClient#getMusicType()}.
	 */
	Event<MusicTypeSelector> EVENT = Event.create(MusicTypeSelector.class, callbacks -> (client, original) -> {
		MusicSound selected = original;
		for (MusicTypeSelector callback : callbacks) {
			selected = callback.select(client, original);
		}
		return selected;
	});

	@ClientOnly
	@FunctionalInterface
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
