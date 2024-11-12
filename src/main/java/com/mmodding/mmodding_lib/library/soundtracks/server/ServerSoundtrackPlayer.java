package com.mmodding.mmodding_lib.library.soundtracks.server;

import com.mmodding.mmodding_lib.library.soundtracks.Soundtrack;
import com.mmodding.mmodding_lib.library.soundtracks.SoundtrackPlayer;
import com.mmodding.mmodding_lib.networking.CommonOperations;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class ServerSoundtrackPlayer implements SoundtrackPlayer {

	private final ServerPlayerEntity player;

	@Nullable
	private Soundtrack currentSoundtrack;

	private int currentPart;

	public ServerSoundtrackPlayer(ServerPlayerEntity player) {
		this.player = player;
		this.currentSoundtrack = null;
		this.currentPart = -1;
	}

	@Override
	public void play(Soundtrack soundtrack, int part) {
		this.currentSoundtrack = soundtrack;
		this.currentPart = part;
		this.send();
	}

	@Override
	public void skip() {
		if (this.currentSoundtrack != null && this.currentPart != -1) {
			if (this.currentPart == this.currentSoundtrack.getPartsCount() - 1) {
				this.stop();
			}
		}
	}

	@Override
	public void skip(int part) {
		if (this.currentSoundtrack != null) {
			if (part < this.currentSoundtrack.getPartsCount()) {
				this.currentPart = part;
				this.send();
			}
		}
	}

	@Override
	public void stop() {
		this.currentSoundtrack = null;
		this.currentPart = -1;
		this.send();
	}

	private void send() {
		if (this.currentSoundtrack != null && this.currentPart != -1) {
			CommonOperations.sendSoundtrackActivity(this.player, this.currentSoundtrack, this.currentPart);
		}
		else {
			CommonOperations.clearSoundtrackActivity(this.player);
		}
	}
}
