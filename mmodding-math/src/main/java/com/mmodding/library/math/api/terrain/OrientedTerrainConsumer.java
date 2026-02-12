package com.mmodding.library.math.api.terrain;

import com.mmodding.library.math.api.OrientedBlockPos;
import com.mmodding.library.math.impl.terrain.OrientedTerrainConsumerImpl;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.function.Consumer;

public interface OrientedTerrainConsumer {

	static OrientedTerrainConsumer create(BlockPos basePos, Consumer<OrientedBlockPos> consumer) {
		return new OrientedTerrainConsumerImpl(basePos, consumer);
	}

	void apply(Direction direction);
}
