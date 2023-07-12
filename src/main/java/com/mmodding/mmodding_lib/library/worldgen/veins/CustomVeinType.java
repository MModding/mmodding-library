package com.mmodding.mmodding_lib.library.worldgen.veins;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.PositionalRandomFactory;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.gen.DensityFunction;
import net.minecraft.world.gen.chunk.ChunkNoiseSampler;
import org.apache.commons.lang3.mutable.MutableDouble;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomVeinType {

	private final int minY;
	private final int maxY;
	private final VeinStateGroup oreStates;
	private final VeinStateGroup rawOreStates;
	private final VeinStateGroup fillerStates;
	private final MutableFloat veinThreshold = new MutableFloat(0.4f);
	private final AtomicInteger beginEdgeRoundoff = new AtomicInteger(20);
	private final MutableDouble maxEdgeRoundoff = new MutableDouble(0.2);
	private final MutableFloat veinSolidness = new MutableFloat(0.7f);
	private final MutableFloat minRichness = new MutableFloat(0.1f);
	private final MutableFloat maxRichness = new MutableFloat(0.3f);
	private final MutableFloat maxRichnessThreshold = new MutableFloat(0.6f);
	private final MutableFloat rawOreBlockChance = new MutableFloat(0.02f);
	private final MutableFloat minGapNoiseOreSkipThreshold = new MutableFloat(-0.3f);

	public CustomVeinType(int minY, int maxY, VeinStateGroup oreStates, VeinStateGroup rawOreStates, VeinStateGroup fillerStates) {
		this.minY = minY;
		this.maxY = maxY;
		this.oreStates = oreStates;
		this.rawOreStates = rawOreStates;
		this.fillerStates = fillerStates;
	}

	public CustomVeinType setVeinThreshold(float veinThreshold) {
		this.veinThreshold.setValue(veinThreshold);
		return this;
	}

	public CustomVeinType setBeginEdgeRoundoff(int beginEdgeRoundoff) {
		this.beginEdgeRoundoff.set(beginEdgeRoundoff);
		return this;
	}

	public CustomVeinType setMaxEdgeRoundoff(double maxEdgeRoundoff) {
		this.maxEdgeRoundoff.setValue(maxEdgeRoundoff);
		return this;
	}

	public CustomVeinType setVeinSolidness(float veinSolidness) {
		this.veinSolidness.setValue(veinSolidness);
		return this;
	}

	public CustomVeinType setMinRichness(float minRichness) {
		this.minRichness.setValue(minRichness);
		return this;
	}

	public CustomVeinType setMaxRichness(float maxRichness) {
		this.maxRichness.setValue(maxRichness);
		return this;
	}

	public CustomVeinType setMaxRichnessThreshold(float maxRichnessThreshold) {
		this.maxRichnessThreshold.setValue(maxRichnessThreshold);
		return this;
	}

	public CustomVeinType setRawOreBlockChance(float rawOreBlockChance) {
		this.rawOreBlockChance.setValue(rawOreBlockChance);
		return this;
	}

	public CustomVeinType setMinGapNoiseOreSkipThreshold(float minGapNoiseOreSkipThreshold) {
		this.minGapNoiseOreSkipThreshold.setValue(minGapNoiseOreSkipThreshold);
		return this;
	}

	public int getMinY() {
		return minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public BlockState pickOreState(RandomGenerator random) {
		return this.oreStates.getRandom(random);
	}

	public BlockState pickRawOreState(RandomGenerator random) {
		return this.rawOreStates.getRandom(random);
	}

	public BlockState pickFillerState(RandomGenerator random) {
		return this.fillerStates.getRandom(random);
	}

	public ChunkNoiseSampler.BlockStateSampler createCustomVein(DensityFunction veinToggle, DensityFunction veinRidged, DensityFunction veinGap, PositionalRandomFactory posRandom) {
		return ctx -> {
			double toggle = veinToggle.compute(ctx);
			int y = ctx.blockY();
			double absToggle = Math.abs(toggle);
			int maxSub = this.getMaxY() - y;
			int minSub = y - this.getMinY();
			if (maxSub >= 0 && minSub >= 0) {
				int minBetweenSubs = Math.min(maxSub, minSub);
				double clampedA = MathHelper.clampedMap(minBetweenSubs, 0.0, this.beginEdgeRoundoff.get(), -this.maxEdgeRoundoff.getValue(), 0.0);
				if (absToggle + clampedA >= this.veinThreshold.getValue()) {
					RandomGenerator random = posRandom.create(ctx.blockX(), y, ctx.blockZ());
					if (random.nextFloat() <= this.veinSolidness.getValue() && veinRidged.compute(ctx) < 0) {
						double clampedB = MathHelper.clampedMap(absToggle, this.veinThreshold.getValue(), this.maxRichnessThreshold.getValue(), this.minRichness.getValue(), this.maxRichness.getValue());
						if (random.nextFloat() < clampedB && veinGap.compute(ctx) > this.minGapNoiseOreSkipThreshold.getValue()) {
							return random.nextFloat() < this.rawOreBlockChance.getValue() ? this.pickRawOreState(random) : this.pickOreState(random);
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

	public static class VeinStateGroup {

		private final List<BlockState> states;

		public static VeinStateGroup single(Block block) {
			return new VeinStateGroup(List.of(block.getDefaultState()));
		}

		public static VeinStateGroup single(BlockState state) {
			return new VeinStateGroup(List.of(state));
		}

		public static VeinStateGroup create(Block... blocks) {
			List<BlockState> blockStates = new ArrayList<>();
			List.of(blocks).forEach(block -> blockStates.add(block.getDefaultState()));
			return new VeinStateGroup(blockStates);
		}

		public static VeinStateGroup create(BlockState... states) {
			return new VeinStateGroup(List.of(states));
		}

		private VeinStateGroup(List<BlockState> states) {
			this.states = states;
		}

		public BlockState getRandom(RandomGenerator random) {
			return this.states.get(random.nextInt(this.states.size()));
		}
	}
}
