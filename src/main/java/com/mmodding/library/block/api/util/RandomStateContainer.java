package com.mmodding.library.block.api.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.random.RandomGenerator;

import java.util.Arrays;
import java.util.List;

public class RandomStateContainer {

	private final List<BlockState> states;

	public static RandomStateContainer create(Block... blocks) {
		return new RandomStateContainer(Arrays.stream(blocks).map(Block::getDefaultState).toList());
	}

	public static RandomStateContainer create(BlockState... states) {
		return new RandomStateContainer(List.of(states));
	}

	private RandomStateContainer(List<BlockState> states) {
		this.states = states;
	}

	public BlockState getRandom(RandomGenerator random) {
		return this.states.get(random.nextInt(this.states.size()));
	}
}
