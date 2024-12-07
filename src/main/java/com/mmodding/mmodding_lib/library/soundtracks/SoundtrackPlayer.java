package com.mmodding.mmodding_lib.library.soundtracks;

public interface SoundtrackPlayer {

	default void play(Soundtrack soundtrack) {
		this.play(soundtrack, 0);
	}

	default void play(Soundtrack soundtrack, int part) {
		throw new IllegalArgumentException();
	}

	default void playOnce(Soundtrack soundtrack) {
		this.play(soundtrack, 0);
	}

	default void playOnce(Soundtrack soundtrack, int part) {
		throw new IllegalArgumentException();
	}

	default void skip() {
		throw new IllegalArgumentException();
	}

	default void skip(int part) {
		throw new IllegalArgumentException();
	}

	default void stop() {
		throw new IllegalArgumentException();
	}
}
