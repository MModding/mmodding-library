package com.mmodding.mmodding_lib.library.worldgen.veins;

import com.google.common.util.concurrent.AtomicDouble;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.PositionalRandomFactory;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.gen.DensityFunction;
import net.minecraft.world.gen.chunk.ChunkNoiseSampler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CustomVeinType {

	private final int minY;
	private final int maxY;
	private final VeinStateGroup oreStates;
	private final VeinStateGroup rawOreStates;
	private final VeinStateGroup fillerStates;
	private final AtomicDouble veinThreshold = new AtomicDouble(0.4f);
	private final AtomicInteger beginEdgeRoundoff = new AtomicInteger(20);
	private final AtomicDouble maxEdgeRoundoff = new AtomicDouble(0.2);
	private final AtomicDouble veinSolidness = new AtomicDouble(0.7f);
	private final AtomicDouble minRichness = new AtomicDouble(0.1f);
	private final AtomicDouble maxRichness = new AtomicDouble(0.3f);
	private final AtomicDouble maxRichnessThreshold = new AtomicDouble(0.6f);
	private final AtomicDouble rawOreBlockChance = new AtomicDouble(0.02f);
	private final AtomicDouble minGapNoiseOreSkipThreshold = new AtomicDouble(-0.3f);

	public CustomVeinType(int minY, int maxY, VeinStateGroup oreStates, VeinStateGroup rawOreStates, VeinStateGroup fillerStates) {
		this.minY = minY;
		this.maxY = maxY;
		this.oreStates = oreStates;
		this.rawOreStates = rawOreStates;
		this.fillerStates = fillerStates;
	}

	public CustomVeinType setVeinThreshold(float veinThreshold) {
		this.veinThreshold.set(veinThreshold);
		return this;
	}

	public CustomVeinType setBeginEdgeRoundoff(int beginEdgeRoundoff) {
		this.beginEdgeRoundoff.set(beginEdgeRoundoff);
		return this;
	}

	public CustomVeinType setMaxEdgeRoundoff(double maxEdgeRoundoff) {
		this.maxEdgeRoundoff.set(maxEdgeRoundoff);
		return this;
	}

	public CustomVeinType setVeinSolidness(float veinSolidness) {
		this.veinSolidness.set(veinSolidness);
		return this;
	}

	public CustomVeinType setMinRichness(float minRichness) {
		this.minRichness.set(minRichness);
		return this;
	}

	public CustomVeinType setMaxRichness(float maxRichness) {
		this.maxRichness.set(maxRichness);
		return this;
	}

	public CustomVeinType setMaxRichnessThreshold(float maxRichnessThreshold) {
		this.maxRichnessThreshold.set(maxRichnessThreshold);
		return this;
	}

	public CustomVeinType setRawOreBlockChance(float rawOreBlockChance) {
		this.rawOreBlockChance.set(rawOreBlockChance);
		return this;
	}

	public CustomVeinType setMinGapNoiseOreSkipThreshold(float minGapNoiseOreSkipThreshold) {
		this.minGapNoiseOreSkipThreshold.set(minGapNoiseOreSkipThreshold);
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
				double clampedA = MathHelper.clampedMap(minBetweenSubs, 0.0, this.beginEdgeRoundoff.get(), -this.maxEdgeRoundoff.get(), 0.0);
				if (absToggle + clampedA >= this.veinThreshold.get()) {
					RandomGenerator random = posRandom.create(ctx.blockX(), y, ctx.blockZ());
					if (random.nextFloat() <= this.veinSolidness.get() && veinRidged.compute(ctx) < 0) {
						double clampedB = MathHelper.clampedMap(absToggle, this.veinThreshold.get(), this.maxRichnessThreshold.get(), this.minRichness.get(), this.maxRichness.get());
						if (random.nextFloat() < clampedB && veinGap.compute(ctx) > this.minGapNoiseOreSkipThreshold.get()) {
							return random.nextFloat() < this.rawOreBlockChance.get() ? this.pickRawOreState(random) : this.pickOreState(random);
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
