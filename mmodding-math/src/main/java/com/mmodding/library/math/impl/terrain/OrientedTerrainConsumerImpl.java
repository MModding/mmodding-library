package com.mmodding.library.math.impl.terrain;

import com.mmodding.library.math.api.OrientedBlockPos;
import com.mmodding.library.math.api.terrain.OrientedTerrainConsumer;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class OrientedTerrainConsumerImpl implements OrientedTerrainConsumer {

	private final BlockPos basePos;
	private final Consumer<OrientedBlockPos> consumer;

	public OrientedTerrainConsumerImpl(BlockPos basePos, Consumer<OrientedBlockPos> consumer) {
		this.basePos = basePos;
		this.consumer = consumer;
	}

	public void apply(Direction front, Direction up) {
		this.consumer.accept(OrientedBlockPos.of(front, up, this.basePos));
	}
}
