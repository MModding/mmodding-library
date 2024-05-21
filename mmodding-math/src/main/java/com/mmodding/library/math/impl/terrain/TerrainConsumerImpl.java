package com.mmodding.library.math.impl.terrain;

import com.mmodding.library.math.api.terrain.TerrainConsumer;
import net.minecraft.util.math.BlockPos;

import java.util.function.Consumer;

public class TerrainConsumerImpl implements TerrainConsumer {

	private final BlockPos basePos;
	private final Consumer<BlockPos> consumer;

	public TerrainConsumerImpl(BlockPos basePos, Consumer<BlockPos> consumer) {
		this.basePos = basePos;
		this.consumer = consumer;
	}

	public void apply() {
		this.consumer.accept(this.basePos);
	}
}
