package com.mmodding.mmodding_lib.library.soundtracks.client;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.library.sounds.client.SoundQueue;
import com.mmodding.mmodding_lib.library.soundtracks.Soundtrack;
import com.mmodding.mmodding_lib.library.soundtracks.SoundtrackPlayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ClientSoundtrackPlayer implements ClientPlayerTickable, SoundtrackPlayer {

	private final ClientPlayerEntity player;
	private final SoundManager soundManager;

	@Nullable
	private SoundQueue queue = null;

	@Nullable
	private Identifier lock = null;

	private boolean sealed = false;

	@ApiStatus.Internal
	public ClientSoundtrackPlayer(ClientPlayerEntity player, SoundManager soundManager) {
		this.player = player;
		this.soundManager = soundManager;
	}

	@Override
	public void play(Soundtrack soundtrack, int fromPart, int toPart) {
		if ((this.lock == null || this.lock.equals(soundtrack.getIdentifier())) && !this.sealed) {
			boolean needsNewQueue = this.queue == null;
			if (needsNewQueue) {
				this.queue = new SoundQueue(this.soundManager, MModdingLib.createId("soundtracks"), SoundCategory.MUSIC, 1.0f, 1.0f);
			}
			for (int i = 0; i <= toPart - fromPart; i++) {
				for (int j = 0; j < soundtrack.getPart(fromPart + i).getIterations(); j++) {
					this.queue.addTracking(this.player, soundtrack.getPart(fromPart + i).getSound(), soundtrack.getPart(fromPart + i).isLooping());
				}
			}
			MinecraftClient.getInstance().getMusicTracker().stop();
			if (needsNewQueue) {
				MinecraftClient.getInstance().getSoundManager().play(this.queue);
			}
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
		if (this.queue != null) {
			this.queue.release(false);
		}
	}

	@Override
	public void clear() {
		if (this.queue != null) {
			this.queue.clear();
		}
	}

	@Override
	public void stop() {
		MinecraftClient.getInstance().getSoundManager().stop(this.queue);
	}

	@Override
	public void tick() {
		if (this.queue != null && this.queue.getCurrentlyPlaying() == null) {
			this.queue = null;
		}
	}

	public boolean isPlaying() {
		return this.queue != null && this.queue.getCurrentlyPlaying() != null;
	}
}
