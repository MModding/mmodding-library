package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.WorldDuckInterface;
import com.mmodding.mmodding_lib.library.stellar.StellarStatus;
import com.mmodding.mmodding_lib.library.stellar.client.StellarCycle;
import com.mmodding.mmodding_lib.library.utils.MModdingGlobalMaps;
import com.mmodding.mmodding_lib.persistentstates.StellarStatusState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mixin(World.class)
public abstract class WorldMixin implements WorldDuckInterface {

	@Unique
	protected final Map<Identifier, StellarStatus> allStellarStatus = new HashMap<>();

    @Shadow
    public abstract RegistryKey<World> getRegistryKey();

    @Shadow
	@Final
	public RandomGenerator random;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void init(MutableWorldProperties mutableWorldProperties, RegistryKey<World> registryKey, Holder<DimensionType> holder, Supplier<Profiler> supplier, boolean bl, boolean bl2, long l, int i, CallbackInfo ci) {
		MModdingGlobalMaps.getStellarCycleKeys().forEach((identifier) -> {
			StellarCycle stellarCycle = MModdingGlobalMaps.getStellarCycle(identifier);
			if (stellarCycle.getWorldKey().equals(this.getRegistryKey())) {
				this.allStellarStatus.put(identifier, StellarStatus.of(stellarCycle));
			}
		});
	}

	@Override
	public Map<Identifier, StellarStatus> mmodding_lib$getAllStellarStatus() {
		return this.allStellarStatus;
	}

	@Override
	public void mmodding_lib$putStellarStatus(Identifier identifier, StellarStatus status) {
		this.allStellarStatus.put(identifier, status);
	}

	@Override
	public StellarStatus mmodding_lib$getStellarStatus(Identifier identifier) {
		return this.allStellarStatus.get(identifier);
	}

	@Override
	public StellarStatusState mmodding_lib$createStellarStatusState() {
		return new StellarStatusState((World) (Object) this);
	}

	@Override
	public StellarStatusState mmodding_lib$stellarStatusStateFromNbt(NbtCompound nbt) {
		return this.mmodding_lib$createStellarStatusState().readNbt(nbt);
	}
}
