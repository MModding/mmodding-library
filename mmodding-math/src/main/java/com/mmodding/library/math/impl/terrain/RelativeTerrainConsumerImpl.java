package com.mmodding.library.math.impl.terrain;

import com.mmodding.library.math.api.RelativeBlockPos;
import com.mmodding.library.math.api.terrain.RelativeTerrainConsumer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.function.Consumer;

public class RelativeTerrainConsumerImpl implements RelativeTerrainConsumer {

	private final BlockPos basePos;
	private final Consumer<RelativeBlockPos> consumer;

	public RelativeTerrainConsumerImpl(BlockPos basePos, Consumer<RelativeBlockPos> consumer) {
		this.basePos = basePos;
		this.consumer = consumer;
	}

	public void apply(Direction direction) {
		this.consumer.accept(RelativeBlockPos.of(this.basePos).apply(direction));
	}
}
