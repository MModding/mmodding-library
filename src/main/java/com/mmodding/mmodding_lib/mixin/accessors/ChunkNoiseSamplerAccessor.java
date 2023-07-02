package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.world.gen.chunk.ChunkNoiseSampler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChunkNoiseSampler.class)
public interface ChunkNoiseSamplerAccessor {

	@Accessor("blockStateSampler")
	void setBlockStateSampler(ChunkNoiseSampler.BlockStateSampler sampler);
}
