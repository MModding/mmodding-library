package com.mmodding.mmodding_lib.library.soundtracks;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.List;

public class Soundtrack {

	private final Identifier identifier;
	private final List<Part> parts;

	private Soundtrack(Identifier identifier, List<Part> parts) {
		this.identifier = identifier;
		this.parts = parts;
	}

	public static Soundtrack create(Identifier identifier, Part... parts) {
		return Soundtrack.create(identifier, List.of(parts));
	}

	public static Soundtrack create(Identifier identifier, List<Part> parts) {
		return new Soundtrack(identifier, parts);
	}

	public Identifier getIdentifier() {
		return this.identifier;
	}

	public int getPartsCount() {
		return this.parts.size();
	}

	public Part getPart(int part) {
		return this.parts.get(part);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Soundtrack soundtrack) {
			return this.identifier.equals(soundtrack.identifier);
		}
		else {
			return super.equals(obj);
		}
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
