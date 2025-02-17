package com.mmodding.mmodding_lib.library.soundtracks.client;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.library.sounds.client.SoundQueue;
import com.mmodding.mmodding_lib.library.soundtracks.Soundtrack;
import com.mmodding.mmodding_lib.library.soundtracks.SoundtrackPlayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.util.ClientPlayerTickable;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.stream.IntStream;

@ClientOnly
public class ClientSoundtrackPlayer implements ClientPlayerTickable, SoundtrackPlayer {

	private final ClientPlayerEntity player;
	private final SoundManager soundManager;

	@Nullable
	private SoundQueue queue = null;

	@Nullable
	private Soundtrack currentSoundtrack = null;

	@ApiStatus.Internal
	public ClientSoundtrackPlayer(ClientPlayerEntity player, SoundManager soundManager) {
		this.player = player;
		this.soundManager = soundManager;
	}

	@Override
	public void append(Soundtrack soundtrack, int... parts) {
		boolean needsNewQueue = this.queue == null;
		if (needsNewQueue) {
			this.queue = new SoundQueue(this.soundManager, MModdingLib.createId("soundtracks"), 1.0f, 1.0f);
		}
		for (int part : parts) {
			this.queue.addTracking(this.player, soundtrack.getPart(part).getSound(), soundtrack.getPart(part).isLooping());
		}
		MinecraftClient.getInstance().getMusicTracker().stop();
		if (needsNewQueue) {
			this.soundManager.play(this.queue);
		}
	}

	@Override
	public void play(Soundtrack soundtrack, int part) {
		this.currentSoundtrack = soundtrack;
		this.append(soundtrack, IntStream.range(0, soundtrack.getPartsCount() - part).toArray());
	}

	@Override
	public void playOnce(Soundtrack soundtrack, int part) {
		if (!soundtrack.equals(this.currentSoundtrack)) {
			this.play(soundtrack, part);
		}
	}

	@Override
	public void skip() {
		if (this.queue != null) {
			this.queue.forward(1);
		}
	}

	@Override
	public void skip(int part) {
		if (this.currentSoundtrack != null) {
			if (this.queue != null) {
				this.queue.stop();
			}
			this.play(this.currentSoundtrack, part);
		}
	}

	@Override
	public void stop() {
		if (this.queue != null) {
			this.currentSoundtrack = null;
			this.queue.stop();
		}
	}

	@Override
	public void tick() {
		if (this.queue != null) {
			if (this.queue.getLength() == 0) {
				this.currentSoundtrack = null;
			}
		}
	}

	public boolean isPlaying() {
		return this.currentSoundtrack != null;
	}
}
