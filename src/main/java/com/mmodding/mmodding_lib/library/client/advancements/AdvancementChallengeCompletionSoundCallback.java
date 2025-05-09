package com.mmodding.mmodding_lib.library.client.advancements;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.advancement.Advancement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundEvent;

public interface AdvancementChallengeCompletionSoundCallback {

	/**
	 * Allows modifying the selected {@link SoundEvent} for the current {@link Advancement} challenge to play.
	 */
	Event<CompletionSoundSelector> EVENT = EventFactory.createArrayBacked(CompletionSoundSelector.class, callbacks -> (client, advancement, original) -> {
		SoundEvent selected = original;
		for (CompletionSoundSelector callback : callbacks) {
			selected = callback.select(client, advancement, original);
		}
		return selected;
	});

	@FunctionalInterface
	@Environment(EnvType.CLIENT)
	interface CompletionSoundSelector {

		/**
		 * Modifies the selected {@link SoundEvent} for the current {@link Advancement} challenge to play.
		 * @param client the minecraft client
		 * @param advancement the current advancement
		 * @param original the originally selected sound event
		 * @return the newly selected sound event
		 */
		SoundEvent select(MinecraftClient client, Advancement advancement, SoundEvent original);
	}
}
