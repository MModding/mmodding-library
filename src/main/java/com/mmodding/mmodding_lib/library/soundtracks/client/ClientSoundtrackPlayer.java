package com.mmodding.mmodding_lib.library.soundtracks.client;

import com.mmodding.mmodding_lib.library.soundtracks.Soundtrack;
import com.mmodding.mmodding_lib.library.soundtracks.SoundtrackPlayer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.EntityTrackingSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.util.ClientPlayerTickable;
import net.minecraft.sound.SoundCategory;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class ClientSoundtrackPlayer implements ClientPlayerTickable, SoundtrackPlayer {

	private final ClientPlayerEntity player;
	private final SoundManager soundManager;

	@Nullable
	private EntityTrackingSoundInstance instance = null;

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
			else {
				this.play(this.currentSoundtrack, this.currentPart + 1);
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
			else {
				this.stop();
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
					this.currentSoundtrack.getPart(this.currentPart).getSound(),
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
					if (this.currentSoundtrack.getPart(this.currentPart).isLooping()) {
						this.play(this.currentSoundtrack, this.currentPart);
					}
					else {
						this.skip();
					}
				}
			}
		}
	}
}
