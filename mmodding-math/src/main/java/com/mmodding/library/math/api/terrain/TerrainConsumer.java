package com.mmodding.library.math.api.terrain;

import com.mmodding.library.math.impl.terrain.TerrainConsumerImpl;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;

public interface TerrainConsumer {

	static TerrainConsumer create(BlockPos basePos, Consumer<BlockPos> position) {
		return new TerrainConsumerImpl(basePos, position);
	}

	void apply();
}
