package com.mmodding.mmodding_lib.persistentstates;

import com.mmodding.mmodding_lib.ducks.WorldDuckInterface;
import com.mmodding.mmodding_lib.library.stellar.StellarStatus;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class StellarStatusState extends PersistentState {

	private final World world;

	public StellarStatusState(World world) {
		this.world = world;
	}

	@Override
	public boolean isDirty() {
		return true;
	}

	public StellarStatusState readNbt(NbtCompound nbt) {
		for (String cycle : nbt.getKeys()) {
			NbtCompound compound = nbt.getCompound(cycle);
			StellarStatus status = StellarStatus.of(compound.getLong("current_time"), compound.getLong("full_time"));
			((WorldDuckInterface) this.world).mmodding_lib$putStellarStatus(new Identifier(cycle), status);
		}
		return this;
	}

	@Override
	public NbtCompound writeNbt(NbtCompound nbt) {
		((WorldDuckInterface) this.world).mmodding_lib$getAllStellarStatus().forEach((cycle, status) -> {
			NbtCompound compound = new NbtCompound();
			compound.putLong("current_time", status.getCurrentTime());
			compound.putLong("full_time", status.getFullTime());
			nbt.put(cycle.toString(), compound);
		});
		return nbt;
	}
}
