package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.unmapped.C_kedlqxpi;
import net.minecraft.util.Holder;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChunkSection.class)
public interface ChunkSectionAccessor {

	@Accessor("biomeContainer")
	void setBiomeContainer(C_kedlqxpi<Holder<Biome>> palettedContainer);
}
