package com.mmodding.mmodding_lib.library.sounds.client.stream;

import net.minecraft.client.sound.RepeatingAudioStream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class ReleasableRepeatingAudioStream extends RepeatingAudioStream {

	private boolean released = false;

	public ReleasableRepeatingAudioStream(DelegateFactory delegateFactory, InputStream inputStream) throws IOException {
		super(delegateFactory, inputStream);
	}

	@Override
	public ByteBuffer getBuffer(int size) throws IOException {
		return !this.released ? super.getBuffer(size) : this.delegate.getBuffer(size);
	}

	public void release() {
		this.released = true;
	}
}
