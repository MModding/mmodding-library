package com.mmodding.mmodding_lib.states.readable;

import com.mmodding.mmodding_lib.library.stellar.StellarStatus;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;

@ApiStatus.Internal
public interface ReadableStellarStatuses {

	static ReadableStellarStatuses of(Map<Identifier, StellarStatus> stellarStatuses) {
		return new Impl(stellarStatuses);
	}

	default ReadableStellarStatuses readNbt(NbtCompound nbt) {
		for (String cycle : nbt.getKeys()) {
			NbtCompound compound = nbt.getCompound(cycle);
			StellarStatus status = StellarStatus.of(compound.getLong("current_time"), compound.getLong("full_time"));
			this.getMap().put(new Identifier(cycle), status);
		}
		return this;
	}

	Map<Identifier, StellarStatus> getMap();

	class Impl implements ReadableStellarStatuses {

		private final Map<Identifier, StellarStatus> stellarStatuses;

		public Impl(Map<Identifier, StellarStatus> stellarStatuses) {
			this.stellarStatuses = stellarStatuses;
		}

		@Override
		public Map<Identifier, StellarStatus> getMap() {
			return this.stellarStatuses;
		}
	}
}
