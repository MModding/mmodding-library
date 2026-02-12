package com.mmodding.library.block.api.util;

import com.mojang.datafixers.util.Either;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.random.Random;

import java.util.Arrays;
import java.util.List;

public class RandomStateContainer {

	private final List<BlockState> states;

	@SafeVarargs
	public static RandomStateContainer create(Either<Block, BlockState>... elements) {
		return new RandomStateContainer(Arrays.stream(elements).map(element -> element.mapLeft(Block::getDefaultState).map(state -> state, state -> state)).toList());
	}

	public static RandomStateContainer create(Block... blocks) {
		return new RandomStateContainer(Arrays.stream(blocks).map(Block::getDefaultState).toList());
	}

	public static RandomStateContainer create(BlockState... states) {
		return new RandomStateContainer(List.of(states));
	}

	private RandomStateContainer(List<BlockState> states) {
		this.states = states;
	}

	public BlockState getRandom(Random random) {
		return this.states.get(random.nextInt(this.states.size()));
	}
}
