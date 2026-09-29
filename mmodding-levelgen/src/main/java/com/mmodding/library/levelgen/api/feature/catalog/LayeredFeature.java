package com.mmodding.library.levelgen.api.feature.catalog;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record LayeredFeature(HolderSet<PlacedFeature> layers) implements Feature {

	public static final MapCodec<LayeredFeature> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
			PlacedFeature.LIST_CODEC.fieldOf("layers").forGetter(LayeredFeature::layers)
		).apply(instance, LayeredFeature::new)
	);

	@Override
	public MapCodec<? extends Feature> codec() {
		return CODEC;
	}

	@Override
	public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
		for (Holder<PlacedFeature> placedFeature : this.layers) {
			placedFeature.value().place(level, chunkGenerator, random, origin);
		}
		return true;
	}
}
