package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.stellar.StellarStatus;
import com.mmodding.mmodding_lib.library.stellar.StellarCycle;
import com.mmodding.mmodding_lib.library.utils.MModdingGlobalMaps;
import com.mmodding.mmodding_lib.library.utils.Self;
import com.mmodding.mmodding_lib.states.readable.ReadableStellarStatuses;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(World.class)
public abstract class WorldMixin implements Self<World> {

    @Shadow
    public abstract RegistryKey<World> getRegistryKey();

    @Shadow
	@Final
	public RandomGenerator random;

	@Unique
	protected <T extends ReadableStellarStatuses> T fillStellarStatuses(T stellarStatuses) {
		MModdingGlobalMaps.getStellarCycleKeys().forEach((identifier) -> {
			StellarCycle stellarCycle = MModdingGlobalMaps.getStellarCycle(identifier);
			if (stellarCycle.getWorldKey().equals(this.getRegistryKey())) {
				stellarStatuses.getMap().put(identifier, StellarStatus.of(stellarCycle));
			}
		});
		return stellarStatuses;
	}
}
