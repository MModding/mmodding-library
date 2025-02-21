package com.mmodding.mmodding_lib.library.soundtracks;

import net.minecraft.util.Identifier;

public interface SoundtrackPlayer {

	/**
	 * Queues all parts of the soundtrack.
	 * @param soundtrack the soundtrack
	 */
	default void play(Soundtrack soundtrack) {
		this.play(soundtrack, 0);
	}

	/**
	 * Queues all parts after the specified part index of the soundtrack.
	 * @param soundtrack the soundtrack
	 * @param fromPart the spêcified part index
	 */
	default void play(Soundtrack soundtrack, int fromPart) {
		this.play(soundtrack, fromPart, soundtrack.getPartsCount() - 1);
	}

	/**
	 * Queues all parts in the specified range of the soundtrack.
	 * @param soundtrack the soundtrack
	 * @param fromPart the index where the range starts
	 * @param toPart the index where the range ends
	 */
	void play(Soundtrack soundtrack, int fromPart, int toPart);

	/**
	 * Prevents any other soundtrack from playing that the specified one.
	 * @param soundtrack the soundtrack
	 */
	default void lock(Soundtrack soundtrack) {
		this.lock(soundtrack.getIdentifier());
	}

	/**
	 * Prevents any soundtrack not matching with the specified identifier from playing.
	 * @param identifier the identifier
	 */
	void lock(Identifier identifier);

	/**
	 * Removes all restrictions preventing sounds from being played.
	 */
	void unlock();

	/**
	 * Seals the soundtrack player, preventing any new soundtrack part from being queued.
	 */
	void seal();

	/**
	 * Unseals the soundtrack player, allowing new soundtrack parts from being queued.
	 */
	void unseal();

	/**
	 * Looks up for looping parts and releases them.
	 */
	void release();

	/**
	 * Clears all queued sounds except the one that is currently playing.
	 */
	void clear();

	/**
	 * Stops all sounds including the current one.
	 */
	void stop();
}
