package com.mmodding.mmodding_lib.library.soundtracks.client;

import com.mmodding.mmodding_lib.library.soundtracks.Soundtrack;
import com.mmodding.mmodding_lib.library.soundtracks.SoundtrackPlayer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.EntityTrackingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class ClientSoundtrackPlayer implements ClientPlayerTickable, SoundtrackPlayer {

	private final ClientPlayerEntity player;
	private final SoundManager soundManager;

	@Nullable
	private SoundInstance instance = null;

	@Nullable
	private Soundtrack currentSoundtrack = null;

	private int currentPart = -1;

	@ApiStatus.Internal
	public ClientSoundtrackPlayer(ClientPlayerEntity player, SoundManager soundManager) {
		this.player = player;
		this.soundManager = soundManager;
	}

	@Override
	public void play(Soundtrack soundtrack, int part) {
		this.currentSoundtrack = soundtrack;
		this.currentPart = part;
	}

	@Override
	public void playOnce(Soundtrack soundtrack, int part) {
		if (this.currentSoundtrack != soundtrack) {
			this.play(soundtrack, part);
		}
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
				this.instance = null;
			}
		}
	}

	@Override
	public void stop() {
		this.currentSoundtrack = null;
		this.currentPart = -1;
		this.instance = null;
	}

	@Override
	public void tick() {
		if (this.currentSoundtrack != null && this.currentPart != -1) {
			if (this.instance == null) {
				this.instance = new EntityTrackingSoundInstance(
					new SoundEvent(this.currentSoundtrack.getPart(this.currentPart).getPath()),
					SoundCategory.MUSIC,
					1.0f,
					1.0f,
					this.player,
					this.player.getRandom().nextLong()
				);
				this.soundManager.play(this.instance);
			}
			else {
				if (!this.soundManager.isPlaying(this.instance)) {
					this.skip();
				}
			}
		}
	}
}
