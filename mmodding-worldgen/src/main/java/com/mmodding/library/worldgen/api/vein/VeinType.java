package com.mmodding.library.worldgen.api.vein;

import com.mmodding.library.block.api.util.RandomStateContainer;
import com.mmodding.library.core.api.registry.Registrable;
import com.mmodding.library.core.api.registry.companion.RegistryCompanion;
import com.mmodding.library.core.api.registry.companion.RegistryKeyAttachment;
import com.mmodding.library.worldgen.impl.vein.VeinTypeImpl;
import net.minecraft.block.BlockState;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.random.PositionalRandomFactory;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.gen.DensityFunction;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.ChunkNoiseSampler;

public interface VeinType extends Registrable<VeinType> {

	RegistryCompanion<ChunkGeneratorSettings, VeinType> REGISTRY = RegistryCompanion.create(RegistryKeyAttachment.dynamic(RegistryKeys.CHUNK_GENERATOR_SETTINGS));

	BlockState pickOreState(RandomGenerator random);

	BlockState pickRawOreState(RandomGenerator random);

	BlockState pickFillerState(RandomGenerator random);

	ChunkNoiseSampler.BlockStateSampler createVein(DensityFunction veinToggle, DensityFunction veinRidged, DensityFunction veinGap, PositionalRandomFactory posRandom);

	class Builder {

		private final int minY;
		private final int maxY;

		private final RandomStateContainer oreStates;
		private final RandomStateContainer rawOreStates;
		private final RandomStateContainer fillerStates;

		private float veinThreshold = 0.4f;
		private int beginEdgeRoundoff = 20;
		private double maxEdgeRoundoff = 0.2;
		private float veinSolidness = 0.7f;
		private float minRichness = 0.1f;
		private float maxRichness = 0.3f;
		private float maxRichnessThreshold = 0.6f;
		private float rawOreBlockChance = 0.02f;
		private float minGapNoiseOreSkipThreshold = -0.3f;

		public Builder(int minY, int maxY, RandomStateContainer oreStates, RandomStateContainer rawOreStates, RandomStateContainer fillerStates) {
			this.minY = minY;
			this.maxY = maxY;
			this.oreStates = oreStates;
			this.rawOreStates = rawOreStates;
			this.fillerStates = fillerStates;
		}

		public Builder setVeinThreshold(float veinThreshold) {
			this.veinThreshold = veinThreshold;
			return this;
		}

		public Builder setBeginEdgeRoundoff(int beginEdgeRoundoff) {
			this.beginEdgeRoundoff = beginEdgeRoundoff;
			return this;
		}

		public Builder setMaxEdgeRoundoff(double maxEdgeRoundoff) {
			this.maxEdgeRoundoff = maxEdgeRoundoff;
			return this;
		}

		public Builder setVeinSolidness(float veinSolidness) {
			this.veinSolidness = veinSolidness;
			return this;
		}

		public Builder setMinRichness(float minRichness) {
			this.minRichness = minRichness;
			return this;
		}

		public Builder setMaxRichness(float maxRichness) {
			this.maxRichness = maxRichness;
			return this;
		}

		public Builder setMaxRichnessThreshold(float maxRichnessThreshold) {
			this.maxRichnessThreshold = maxRichnessThreshold;
			return this;
		}

		public Builder setRawOreBlockChance(float rawOreBlockChance) {
			this.rawOreBlockChance = rawOreBlockChance;
			return this;
		}

		public Builder setMinGapNoiseOreSkipThreshold(float minGapNoiseOreSkipThreshold) {
			this.minGapNoiseOreSkipThreshold = minGapNoiseOreSkipThreshold;
			return this;
		}

		public VeinType build() {
			return new VeinTypeImpl(
				this.minY,
				this.maxY,
				this.oreStates,
				this.rawOreStates,
				this.fillerStates,
				this.veinThreshold,
				this.beginEdgeRoundoff,
				this.maxEdgeRoundoff,
				this.veinSolidness,
				this.minRichness,
				this.maxRichness,
				this.maxRichnessThreshold,
				this.rawOreBlockChance,
				this.minGapNoiseOreSkipThreshold
			);
		}
	}
}
