package com.mmodding.library.worldgen.impl.vein;

import com.mmodding.library.block.api.util.RandomStateContainer;
import com.mmodding.library.worldgen.api.vein.VeinType;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.PositionalRandomFactory;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.gen.DensityFunction;
import net.minecraft.world.gen.chunk.ChunkNoiseSampler;

public class VeinTypeImpl implements VeinType {

	private final int minY;
	private final int maxY;

	private final RandomStateContainer oreStates;
	private final RandomStateContainer rawOreStates;
	private final RandomStateContainer fillerStates;

	private final float veinThreshold;
	private final int beginEdgeRoundoff;
	private final double maxEdgeRoundoff;
	private final float veinSolidness;
	private final float minRichness;
	private final float maxRichness;
	private final float maxRichnessThreshold;
	private final float rawOreBlockChance;
	private final float minGapNoiseOreSkipThreshold;

	public VeinTypeImpl(int minY, int maxY, RandomStateContainer oreStates, RandomStateContainer rawOreStates, RandomStateContainer fillerStates, float veinThreshold, int beginEdgeRoundoff, double maxEdgeRoundoff, float veinSolidness, float minRichness, float maxRichness, float maxRichnessThreshold, float rawOreBlockChance, float minGapNoiseOreSkipThreshold) {
		this.minY = minY;
		this.maxY = maxY;
		this.oreStates = oreStates;
		this.rawOreStates = rawOreStates;
		this.fillerStates = fillerStates;
		this.veinThreshold = veinThreshold;
		this.beginEdgeRoundoff = beginEdgeRoundoff;
		this.maxEdgeRoundoff = maxEdgeRoundoff;
		this.veinSolidness = veinSolidness;
		this.minRichness = minRichness;
		this.maxRichness = maxRichness;
		this.maxRichnessThreshold = maxRichnessThreshold;
		this.rawOreBlockChance = rawOreBlockChance;
		this.minGapNoiseOreSkipThreshold = minGapNoiseOreSkipThreshold;
	}

	@Override
	public BlockState pickOreState(RandomGenerator random) {
		return this.oreStates.getRandom(random);
	}

	@Override
	public BlockState pickRawOreState(RandomGenerator random) {
		return this.rawOreStates.getRandom(random);
	}

	@Override
	public BlockState pickFillerState(RandomGenerator random) {
		return this.fillerStates.getRandom(random);
	}

	@Override
	public ChunkNoiseSampler.BlockStateSampler createVein(DensityFunction veinToggle, DensityFunction veinRidged, DensityFunction veinGap, PositionalRandomFactory posRandom) {
		return ctx -> {
			double toggle = veinToggle.compute(ctx);
			int y = ctx.blockY();
			double absToggle = Math.abs(toggle);
			int maxSub = this.maxY - y;
			int minSub = y - this.minY;
			if (maxSub >= 0 && minSub >= 0) {
				int minBetweenSubs = Math.min(maxSub, minSub);
				double clampedA = MathHelper.clampedMap(minBetweenSubs, 0.0, this.beginEdgeRoundoff, -this.maxEdgeRoundoff, 0.0);
				if (absToggle + clampedA >= this.veinThreshold) {
					RandomGenerator random = posRandom.create(ctx.blockX(), y, ctx.blockZ());
					if (random.nextFloat() <= this.veinSolidness && veinRidged.compute(ctx) < 0) {
						double clampedB = MathHelper.clampedMap(absToggle, this.veinThreshold, this.maxRichnessThreshold, this.minRichness, this.maxRichness);
						if (random.nextFloat() < clampedB && veinGap.compute(ctx) > this.minGapNoiseOreSkipThreshold) {
							return random.nextFloat() < this.rawOreBlockChance ? this.pickRawOreState(random) : this.pickOreState(random);
						}
						else {
							return this.pickFillerState(random);
						}
					}
				}
			}
			return null;
		};
	}
}
