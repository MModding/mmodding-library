package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.ChunkNoiseSamplerDuckInterface;
import net.minecraft.block.BlockState;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.Holder;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.RandomState;
import net.minecraft.world.gen.chunk.*;
import org.apache.commons.lang3.mutable.MutableObject;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.OptionalInt;
import java.util.function.Predicate;

@Mixin(NoiseChunkGenerator.class)
public class NoiseChunkGeneratorMixin {

	@Shadow
	@Final
	protected Holder<ChunkGeneratorSettings> settings;

	@Inject(method = "method_41537", at = @At("TAIL"), cancellable = true)
	private void createChunkNoiseSampler(Chunk chunk, StructureManager structureManager, Blender blender, RandomState randomState, CallbackInfoReturnable<ChunkNoiseSampler> cir) {
		ChunkNoiseSampler sampler = cir.getReturnValue();
		ChunkNoiseSamplerDuckInterface ducked = ((ChunkNoiseSamplerDuckInterface) sampler);
		ducked.mmodding_lib$setSettingsHolder(this.settings);
		ducked.mmodding_lib$reloadBlockStateSampler();
		cir.setReturnValue(sampler);
	}

	@Inject(method = "sampleHeightmap", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/chunk/ChunkNoiseSampler;sampleStartNoise()V", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private void sampleHeightmap(HeightLimitView world, RandomState randomState, int x, int z, @Nullable MutableObject<VerticalBlockSample> mutableObject, @Nullable Predicate<BlockState> predicate, CallbackInfoReturnable<OptionalInt> cir, GenerationShapeConfig generationShapeConfig, int i, int j, int k, int l, BlockState[] blockStates, int m, int n, int o, int p, int q, int r, int s, double d, double e, ChunkNoiseSampler chunkNoiseSampler) {
		ChunkNoiseSamplerDuckInterface ducked = ((ChunkNoiseSamplerDuckInterface) chunkNoiseSampler);
		ducked.mmodding_lib$setSettingsHolder(this.settings);
		ducked.mmodding_lib$reloadBlockStateSampler();
	}
}
