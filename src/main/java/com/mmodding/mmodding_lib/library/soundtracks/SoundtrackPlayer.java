package com.mmodding.mmodding_lib.library.soundtracks;

public interface SoundtrackPlayer {

	default void play(Soundtrack soundtrack) {
		this.play(soundtrack, 0);
	}

	void play(Soundtrack soundtrack, int part);

	void skip();

	void skip(int part);

	void stop();
}
