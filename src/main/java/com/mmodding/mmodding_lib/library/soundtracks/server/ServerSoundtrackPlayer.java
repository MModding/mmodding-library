package com.mmodding.mmodding_lib.library.soundtracks.server;

import com.mmodding.mmodding_lib.library.soundtracks.Soundtrack;
import com.mmodding.mmodding_lib.library.soundtracks.SoundtrackPlayer;
import com.mmodding.mmodding_lib.networking.CommonOperations;
import net.minecraft.server.network.ServerPlayerEntity;

public class ServerSoundtrackPlayer implements SoundtrackPlayer {

	private final ServerPlayerEntity player;

	public ServerSoundtrackPlayer(ServerPlayerEntity player) {
		this.player = player;
	}

	@Override
	public void append(Soundtrack soundtrack, int... parts) {
		CommonOperations.appendSoundtrackForPlayer(this.player, soundtrack, parts);
	}

	@Override
	public void play(Soundtrack soundtrack, int part) {
		CommonOperations.playSoundtrackForPlayer(this.player, soundtrack, part, true);
	}

	@Override
	public void playOnce(Soundtrack soundtrack, int part) {
		CommonOperations.playSoundtrackForPlayer(this.player, soundtrack, part, false);
	}

	@Override
	public void skip() {
		CommonOperations.skipSoundtrackPartForPlayer(this.player);
	}

	@Override
	public void skip(int part) {
		CommonOperations.skipToPartForPlayer(this.player, part);
	}

	@Override
	public void stop() {
		CommonOperations.clearSoundtrackForPlayer(this.player);
	}
}
