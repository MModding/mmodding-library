package com.mmodding.mmodding_lib.library.soundtracks.server;

import com.mmodding.mmodding_lib.library.soundtracks.Soundtrack;
import com.mmodding.mmodding_lib.library.soundtracks.SoundtrackPlayer;
import com.mmodding.mmodding_lib.networking.CommonOperations;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class ServerSoundtrackPlayer implements SoundtrackPlayer {

	private final ServerPlayerEntity player;

	@Nullable
	private Identifier lock = null;

	private boolean sealed = false;

	public ServerSoundtrackPlayer(ServerPlayerEntity player) {
		this.player = player;
	}

	@Override
	public void play(Soundtrack soundtrack, int fromPart, int toPart) {
		if ((this.lock == null || this.lock.equals(soundtrack.getIdentifier())) && !this.sealed) {
			CommonOperations.playSoundtrackForPlayer(this.player, soundtrack, fromPart, toPart);
		}
	}

	@Override
	public void lock(Identifier identifier) {
		this.lock = identifier;
	}

	@Override
	public void unlock() {
		this.lock = null;
	}

	@Override
	public void seal() {
		this.sealed = true;
	}

	@Override
	public void unseal() {
		this.sealed = false;
	}

	@Override
	public void release() {
		CommonOperations.releaseSoundtrackForPlayer(this.player);
	}

	@Override
	public void clear() {
		CommonOperations.clearSoundtrackForPlayer(this.player);
	}

	@Override
	public void stop() {
		CommonOperations.stopSoundtrackForPlayer(this.player);
	}
}
