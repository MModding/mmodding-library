package com.mmodding.mmodding_lib.library.soundtracks;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.List;

public class Soundtrack {

	private final List<Part> parts;

	private Soundtrack(List<Part> parts) {
		this.parts = parts;
	}

	public static Soundtrack create(Part... parts) {
		return Soundtrack.create(List.of(parts));
	}

	public static Soundtrack create(List<Part> parts) {
		return new Soundtrack(parts);
	}

	public int getPartsCount() {
		return this.parts.size();
	}

	public Part getPart(int part) {
		return this.parts.get(part);
	}

	public static class Part {

		private final SoundEvent sound;
		private final boolean looping;
		private final int iterations;

		private Part(Identifier path, boolean looping, int iterations) {
			this.sound = new SoundEvent(path);
			this.looping = looping;
			this.iterations = iterations;
		}

		public static Part looping(Identifier path) {
			return new Part(path, true, 1);
		}

		public static Part iterations(Identifier path, int iterations) {
			return new Part(path, false, iterations);
		}

		public SoundEvent getSound() {
			return this.sound;
		}

		public boolean isLooping() {
			return this.looping;
		}

		public int getIterations() {
			return this.iterations;
		}
	}
}
