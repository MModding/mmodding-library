package com.mmodding.library.math.api.terrain;

import com.mmodding.library.math.api.OrientedBlockPos;
import com.mmodding.library.math.impl.terrain.OrientedTerrainConsumerImpl;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public interface OrientedTerrainConsumer {

	static OrientedTerrainConsumer create(BlockPos basePos, Consumer<OrientedBlockPos> consumer) {
		return new OrientedTerrainConsumerImpl(basePos, consumer);
	}

	void apply(Direction front, Direction up);
}
