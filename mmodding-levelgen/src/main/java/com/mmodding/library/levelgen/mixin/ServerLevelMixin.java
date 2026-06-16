package com.mmodding.library.levelgen.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mmodding.library.levelgen.impl.seed.LevelSeedsImpl;
import com.mmodding.library.levelgen.impl.seed.LevelSeedsStorage;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Objects;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level {

	@Shadow
	public abstract MinecraftServer getServer();

	protected ServerLevelMixin(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeRegistration, boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
		super(levelData, dimension, registryAccess, dimensionTypeRegistration, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
	}

	@WrapMethod(method = "getSeed")
	private long redirectSeedIfIndependent(Operation<Long> original) {
		if (LevelSeedsImpl.HAVE_INDEPENDENT_SEEDS.contains(this.dimension())) {
			MinecraftServer server = Objects.requireNonNull(this.getServer());
			LevelSeedsStorage storage = Objects.requireNonNull(server.getDataStorage().get(LevelSeedsStorage.TYPE));
			return storage.getOrGenerateLevelSeed(this.dimension());
		}
		else {
			return original.call();
		}
	}
}
