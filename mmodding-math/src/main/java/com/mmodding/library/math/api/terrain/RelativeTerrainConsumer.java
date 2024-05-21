package com.mmodding.library.math.api.terrain;

import com.mmodding.library.math.api.RelativeBlockPos;
import com.mmodding.library.math.impl.terrain.RelativeTerrainConsumerImpl;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.function.Consumer;

public interface RelativeTerrainConsumer {

	static RelativeTerrainConsumer create(BlockPos basePos, Consumer<RelativeBlockPos> consumer) {
		return new RelativeTerrainConsumerImpl(basePos, consumer);
	}

	void apply(Direction direction);
}
