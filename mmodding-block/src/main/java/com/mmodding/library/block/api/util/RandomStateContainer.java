package com.mmodding.library.block.api.util;

import com.mojang.datafixers.util.Either;
import java.util.Arrays;
import java.util.List;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class RandomStateContainer {

	private final List<BlockState> states;

	@SafeVarargs
	public static RandomStateContainer create(Either<Block, BlockState>... elements) {
		return new RandomStateContainer(Arrays.stream(elements).map(element -> element.mapLeft(Block::defaultBlockState).map(state -> state, state -> state)).toList());
	}

	public static RandomStateContainer create(Block... blocks) {
		return new RandomStateContainer(Arrays.stream(blocks).map(Block::defaultBlockState).toList());
	}

	public static RandomStateContainer create(BlockState... states) {
		return new RandomStateContainer(List.of(states));
	}

	private RandomStateContainer(List<BlockState> states) {
		this.states = states;
	}

	public BlockState getRandom(RandomSource random) {
		return this.states.get(random.nextInt(this.states.size()));
	}
}
