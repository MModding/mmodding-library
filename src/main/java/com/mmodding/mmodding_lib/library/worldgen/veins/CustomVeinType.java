package com.mmodding.mmodding_lib.library.worldgen.veins;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.PositionalRandomFactory;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.gen.DensityFunction;
import net.minecraft.world.gen.chunk.ChunkNoiseSampler;

import java.util.ArrayList;
import java.util.List;

public class CustomVeinType {

	private final int minY;
	private final int maxY;
	private final VeinStateGroup oreStates;
	private final VeinStateGroup rawOreStates;
	private final VeinStateGroup fillerStates;

	public CustomVeinType(int minY, int maxY, VeinStateGroup oreStates, VeinStateGroup rawOreStates, VeinStateGroup fillerStates) {
		this.minY = minY;
		this.maxY = maxY;
		this.oreStates = oreStates;
		this.rawOreStates = rawOreStates;
		this.fillerStates = fillerStates;
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
				double clampedA = MathHelper.clampedMap(minBetweenSubs, 0.0, 20.0, -0.2, 0.0);
				if (absToggle + clampedA >= 0.4f) {
					RandomGenerator random = posRandom.create(ctx.blockX(), y, ctx.blockZ());
					if (random.nextFloat() <= 0.7f && veinRidged.compute(ctx) < 0) {
						double clampedB = MathHelper.clampedMap(absToggle, 0.4F, 0.6F, 0.1F, 0.3F);
						if (random.nextFloat() < clampedB && veinGap.compute(ctx) > -0.3f) {
							return random.nextFloat() < 0.02f ? this.pickRawOreState(random) : this.pickOreState(random);
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
