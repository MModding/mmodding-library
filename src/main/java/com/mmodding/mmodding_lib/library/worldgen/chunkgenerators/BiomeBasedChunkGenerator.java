package com.mmodding.mmodding_lib.library.worldgen.chunkgenerators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Holder;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryOps;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.structure.StructureSet;

public class BiomeBasedChunkGenerator extends NoiseChunkGenerator {

	public static final Codec<BiomeBasedChunkGenerator> CODEC = RecordCodecBuilder.create(instance -> method_41042(instance)
		.and(instance.group(
			RegistryOps.getRegistry(Registry.NOISE_KEY).forGetter(generator -> generator.noises),
			BiomeSource.CODEC.fieldOf("biome_source").forGetter(generator -> generator.populationSource),
			ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings").forGetter(generator -> generator.settings)
		))
		.apply(instance, instance.stable(BiomeBasedChunkGenerator::new))
	);

	private final Registry<DoublePerlinNoiseSampler.NoiseParameters> noises;
	private final Holder<ChunkGeneratorSettings> settings;

	public BiomeBasedChunkGenerator(Registry<StructureSet> structureSets, Registry<DoublePerlinNoiseSampler.NoiseParameters> noises, BiomeSource biomeSource, Holder<ChunkGeneratorSettings> settings) {
		super(structureSets, noises, biomeSource, settings);
		this.noises = noises;
		this.settings = settings;
	}
}
