package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import com.mmodding.mmodding_lib.library.worldgen.chunkgenerators.BiomeBasedChunkGenerator;
import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.chunk.BuiltinChunkGenerators;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BuiltinChunkGenerators.class)
public class BuiltinChunkGeneratorsMixin {

	@Inject(method = "bootstrap", at = @At("TAIL"))
	private static void bootstrap(Registry<Codec<? extends ChunkGenerator>> registry, CallbackInfoReturnable<Codec<? extends ChunkGenerator>> cir) {
		Registry.register(registry, new MModdingIdentifier("biome_based"), BiomeBasedChunkGenerator.CODEC);
	}
}
