package com.mmodding.mmodding_lib.library.portals;

import net.minecraft.block.FireBlock;

public abstract class Ignition {

	public static Fire ofFire(FireBlock fire) {
		return new Fire(fire);
	}

	public static Key ofKey(CustomPortalKeyItem key) {
		return new Key(key);
	}

	public abstract Method getIgnitionMethod();

	public Fire toFire() {
		return this.getIgnitionMethod() == Method.FIRE ? (Fire) this : null;
	}

	public Key toKey() {
		return this.getIgnitionMethod() == Method.KEY ? (Key) this : null;
	}

	public static class Fire extends Ignition {

		private final FireBlock fire;

		private Fire(FireBlock fire) {
			this.fire = fire;
		}

		@Override
		public Method getIgnitionMethod() {
			return Method.FIRE;
		}

		public FireBlock getFire() {
			return this.fire;
		}
	}

	public static class Key extends Ignition {

		private final CustomPortalKey key;

		private Key(CustomPortalKey key) {
			this.key = key;
		}

		@Override
		public Method getIgnitionMethod() {
			return Method.KEY;
		}

		public CustomPortalKey getKey() {
			return this.key;
		}
	}

	public enum Method {
		FIRE,
		KEY
	}
}
