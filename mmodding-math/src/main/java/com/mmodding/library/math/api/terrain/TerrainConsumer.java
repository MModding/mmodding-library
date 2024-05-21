package com.mmodding.library.math.api.terrain;

import com.mmodding.library.math.impl.terrain.TerrainConsumerImpl;
import net.minecraft.util.math.BlockPos;

import java.util.function.Consumer;

public interface TerrainConsumer {

	static TerrainConsumer create(BlockPos basePos, Consumer<BlockPos> position) {
		return new TerrainConsumerImpl(basePos, position);
	}

	void apply();
}
