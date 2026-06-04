package com.mmodding.library.portal.api.client;

import com.mmodding.library.core.api.MModdingLibrary;
import com.mmodding.library.java.api.color.Color;
import dev.yumi.commons.event.Event;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Portal;
import org.jspecify.annotations.Nullable;

/**
 * A few events about modifying the way the client interacts with the held portal processor.
 */
public class ClientPortalEvents {

	/**
	 * @see EnteringSound
	 */
	public static final Event<Identifier, EnteringSound> ENTERING_SOUND = MModdingLibrary.getEventManager().create(EnteringSound.class, listeners -> (level, player, portal, defaultPitch, defaultVolume) -> {
		for (EnteringSound listener : listeners) {
			EnteringSound.ModulatedSound modulatedSound = listener.changeSound(level, player, portal, defaultPitch, defaultVolume);
			if (modulatedSound != null) {
				return modulatedSound;
			}
		}
		return null;
	});

	/**
	 * @see TransitionSprite
	 */
	public static final Event<Identifier, TransitionSprite> TRANSITION_SPRITE = MModdingLibrary.getEventManager().create(TransitionSprite.class, listeners -> (level, player, portal, portalIntensity, defaultColor) -> {
		for (TransitionSprite listener : listeners) {
			TransitionSprite.ColoredSprite coloredSprite = listener.changeColoredSprite(level, player, portal, portalIntensity, defaultColor);
			if (coloredSprite != null) {
				return coloredSprite;
			}
		}
		return null;
	});

	@FunctionalInterface
	public interface EnteringSound {

		/**
		 * Changes the sound of entering the portal based on given context.
		 * @param level the client level
		 * @param player the local player
		 * @param portal the portal
		 * @param defaultPitch the default pitch
		 * @param defaultVolume the default volume
		 * @return the modulated sound
		 */
		@Nullable
		ModulatedSound changeSound(ClientLevel level, LocalPlayer player, Portal portal, float defaultPitch, float defaultVolume);

		/**
		 * A record containing information about the sound redirection.
		 * @param soundEvent the sound event
		 * @param pitch the pitch
		 * @param volume the volume
		 */
		record ModulatedSound(SoundEvent soundEvent, float pitch, float volume) {}
	}

	@FunctionalInterface
	public interface TransitionSprite {

		/**
		 * Changes the sprite of the transition when going through the portal.
		 * @param level the client level
		 * @param player the local player
		 * @param portal the portal
		 * @param portalIntensity the default pitch
		 * @param defaultColor the default volume
		 * @return the colored sprite
		 */
		@Nullable
		ColoredSprite changeColoredSprite(ClientLevel level, LocalPlayer player, Portal portal, float portalIntensity, Color defaultColor);

		/**
		 * A record containing information about the sprite redirection.
		 * @param sprite the sprite
		 * @param color the color
		 */
		record ColoredSprite(TextureAtlasSprite sprite, Color color) {}
	}
}
