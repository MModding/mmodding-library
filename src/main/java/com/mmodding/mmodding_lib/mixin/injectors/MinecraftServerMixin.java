package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.GeneratorOptionsDuckInterface;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.gen.Spawner;
import net.minecraft.world.level.ServerWorldProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

	@Inject(method = "createWorlds", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;initScoreboard(Lnet/minecraft/world/PersistentStateManager;)V", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private void createWorlds(WorldGenerationProgressListener worldGenerationProgressListener, CallbackInfo ci, ServerWorldProperties serverWorldProperties, GeneratorOptions generatorOptions, boolean bl, long l, long m, List<Spawner> list, Registry<DimensionOptions> registry, DimensionOptions dimensionOptions, ServerWorld serverWorld, PersistentStateManager persistentStateManager) {
		this.initGeneratorOptions(persistentStateManager, generatorOptions);
	}

	@Unique
	public void initGeneratorOptions(PersistentStateManager persistentStateManager, GeneratorOptions generatorOptions) {
		GeneratorOptionsDuckInterface ducked = (GeneratorOptionsDuckInterface) generatorOptions;
		persistentStateManager.getOrCreate(ducked::stateFromNbt, ducked::createDifferedSeedsState, "differedSeedsState");
	}
}
