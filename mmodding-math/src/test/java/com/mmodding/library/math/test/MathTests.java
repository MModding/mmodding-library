package com.mmodding.library.math.test;

import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.management.content.DefaultContentHolder;
import com.mmodding.library.math.api.terrain.RelativeTerrainConsumer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class MathTests implements DefaultContentHolder {

	@Override
	public void register(AdvancedContainer mod) {
		RelativeTerrainConsumer consumer = RelativeTerrainConsumer.create(new BlockPos(0, 1, 0), pos -> {});
		consumer.apply(Direction.NORTH);
	}
}
