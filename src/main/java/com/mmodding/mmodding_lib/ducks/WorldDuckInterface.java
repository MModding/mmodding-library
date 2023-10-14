package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.stellar.StellarStatus;
import com.mmodding.mmodding_lib.persistentstates.StellarStatusState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.Map;

public interface WorldDuckInterface {

    StellarStatus mmodding_lib$getStellarStatus(Identifier identifier);

	Map<Identifier, StellarStatus> mmodding_lib$getAllStellarStatus();

	void mmodding_lib$putStellarStatus(Identifier identifier, StellarStatus status);

	StellarStatusState mmodding_lib$createStellarStatusState();

	StellarStatusState mmodding_lib$stellarStatusStateFromNbt(NbtCompound nbt);
}
