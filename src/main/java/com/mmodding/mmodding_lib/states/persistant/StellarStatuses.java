package com.mmodding.mmodding_lib.states.persistant;

import com.mmodding.mmodding_lib.library.stellar.StellarStatus;
import com.mmodding.mmodding_lib.networking.CommonOperations;
import com.mmodding.mmodding_lib.states.readable.ReadableStellarStatuses;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.world.PersistentState;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;

@ApiStatus.Internal
public class StellarStatuses extends PersistentState implements ReadableStellarStatuses {

	private final Map<Identifier, StellarStatus> stellarStatuses;

	public StellarStatuses() {
		this.stellarStatuses = new HashMap<>();
	}

	public void tick(MinecraftServer server) {
		this.stellarStatuses.forEach((key, value) -> value.tick());
		this.markDirty();
		server.getPlayerManager().getPlayerList().forEach(CommonOperations::sendStellarStatusesToClient);
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		this.stellarStatuses.forEach((cycle, status) -> {
			NbtCompound compound = new NbtCompound();
			compound.putLong("current_time", status.getCurrentTime());
			compound.putLong("full_time", status.getFullTime());
			nbt.put(cycle.toString(), compound);
		});
		return nbt;
	}

	public Map<Identifier, StellarStatus> getMap() {
		return this.stellarStatuses;
	}
}
