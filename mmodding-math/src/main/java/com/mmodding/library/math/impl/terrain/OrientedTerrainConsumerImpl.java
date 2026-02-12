package com.mmodding.library.math.impl.terrain;

import com.mmodding.library.math.api.OrientedBlockPos;
import com.mmodding.library.math.api.terrain.OrientedTerrainConsumer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.function.Consumer;

public class OrientedTerrainConsumerImpl implements OrientedTerrainConsumer {

	private final BlockPos basePos;
	private final Consumer<OrientedBlockPos> consumer;

	public OrientedTerrainConsumerImpl(BlockPos basePos, Consumer<OrientedBlockPos> consumer) {
		this.basePos = basePos;
		this.consumer = consumer;
	}

	public void apply(Direction direction) {
		this.consumer.accept(OrientedBlockPos.of(direction, this.basePos));
	}
}
