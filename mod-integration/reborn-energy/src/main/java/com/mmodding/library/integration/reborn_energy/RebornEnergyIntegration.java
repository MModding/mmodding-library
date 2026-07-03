package com.mmodding.library.integration.reborn_energy;

import com.mmodding.library.energy.api.access.EnergyAccess;
import com.mmodding.library.energy.api.block.BlockEnergy;
import com.mmodding.library.energy.impl.block.BlockEnergyImpl;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.level.ServerLevel;
import team.reborn.energy.api.EnergyStorage;

public class RebornEnergyIntegration implements ModInitializer {

	// cursed trick to prevent stack overflow when trying to find something where there is nothing
	// hope this is not too bad? should at least work without too many issues, I think
	// the unwanted stacking compat would occur on the same thread, so that should work properly
	private static final ThreadLocal<Boolean> COMPAT_UNATTEMPTED = ThreadLocal.withInitial(() -> Boolean.TRUE);

	@Override
	public void onInitialize() {
		EnergyStorage.SIDED.registerFallback((level, pos, _, _, side) -> {
			if (COMPAT_UNATTEMPTED.get()) {
				if (level instanceof ServerLevel serverLevel) {
					COMPAT_UNATTEMPTED.set(Boolean.FALSE);
					EnergyAccess access = BlockEnergy.query(serverLevel, pos, side);
					COMPAT_UNATTEMPTED.set(Boolean.TRUE);
					if (access != null) {
						return new CompatEnergyStorage(access);
					}
				}
			}
			COMPAT_UNATTEMPTED.set(Boolean.TRUE);
			return null;
		});
		BlockEnergyImpl.SIDED.registerFallback((level, pos, state, blockEntity, context) -> {
			if (COMPAT_UNATTEMPTED.get()) {
				COMPAT_UNATTEMPTED.set(Boolean.FALSE);
				EnergyStorage storage = EnergyStorage.SIDED.find(level, pos, state, blockEntity, context);
				COMPAT_UNATTEMPTED.set(Boolean.TRUE);
				if (storage != null) {
					return new CompatEnergyAccess(storage);
				}
			}
			COMPAT_UNATTEMPTED.set(Boolean.TRUE);
			return null;
		});
	}
}
