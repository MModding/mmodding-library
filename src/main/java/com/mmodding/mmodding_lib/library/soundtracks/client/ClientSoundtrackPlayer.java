package com.mmodding.mmodding_lib.library.soundtracks.client;

import com.mmodding.mmodding_lib.library.sounds.client.LoopingSoundInstance;
import com.mmodding.mmodding_lib.library.sounds.client.SoundQueue;
import com.mmodding.mmodding_lib.library.soundtracks.Soundtrack;
import com.mmodding.mmodding_lib.library.soundtracks.SoundtrackPlayer;
import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
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
	private final SoundQueue queue;

	@Nullable
	private Soundtrack currentSoundtrack = null;

	@ApiStatus.Internal
	public ClientSoundtrackPlayer(ClientPlayerEntity player, SoundManager soundManager) {
		this.player = player;
		this.queue = new SoundQueue(soundManager, new MModdingIdentifier("soundtracks"), this.player::getX, this.player::getY, this.player::getZ, true);
		soundManager.play(this.queue);
	}

	private SoundInstance createSoundInstance(SoundEvent sound, boolean isLooping) {
		if (isLooping) {
			return new LoopingSoundInstance<>(new EntityTrackingSoundInstance(
				sound, SoundCategory.MUSIC, 1.0f, 1.0f, this.player, this.player.getRandom().nextLong()
			));
		}
		else {
			return new EntityTrackingSoundInstance(
				sound, SoundCategory.MUSIC, 1.0f, 1.0f, this.player, this.player.getRandom().nextLong()
			);
		}
	}

	@Override
	public void play(Soundtrack soundtrack, int part) {
		this.currentSoundtrack = soundtrack;
		for (int i = 0; i < soundtrack.getPartsCount() - part; i++) {
			this.queue.add(this.createSoundInstance(soundtrack.getPart(part + i).getSound(), soundtrack.getPart(part + i).isLooping()));
		}
	}

	@Override
	public void playOnce(Soundtrack soundtrack, int part) {
		if (!soundtrack.equals(this.currentSoundtrack)) {
			this.currentSoundtrack = soundtrack;
			for (int i = 0; i < soundtrack.getPartsCount() - part; i++) {
				this.queue.add(this.createSoundInstance(soundtrack.getPart(part + i).getSound(), soundtrack.getPart(part + i).isLooping()));
			}
		}
	}

	@Override
	public void skip() {
		if (this.queue.getCurrentlyPlayingOrThrow() instanceof LoopingSoundInstance<?> looping) {
			looping.release();
		}
		this.queue.forward(1);
	}

	@Override
	public void skip(int part) {
		assert this.currentSoundtrack != null;
		if (this.queue.getCurrentlyPlayingOrThrow() instanceof LoopingSoundInstance<?> looping) {
			looping.release();
		}
		this.stop();
		this.play(this.currentSoundtrack, part);
	}

	@Override
	public void stop() {
		this.currentSoundtrack = null;
		this.queue.stop();
	}

	@Override
	public void tick() {
		if (this.queue.getLength() == 0) {
			this.currentSoundtrack = null;
		}
	}
}
