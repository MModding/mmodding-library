package com.mmodding.library.math.test;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.core.api.ExtendedModInitializer;
import com.mmodding.library.math.api.terrain.RelativeTerrainConsumer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class MathTests implements ExtendedModInitializer {

	@Override
	public void setupManager(ElementsManager.Builder manager) {
		manager.content(MathTests::register);
	}

	@Override
	public void onInitialize(AdvancedContainer mod) {}

	public static void register(AdvancedContainer mod) {
		RelativeTerrainConsumer consumer = RelativeTerrainConsumer.create(new BlockPos(0, 1, 0), pos -> {});
		consumer.apply(Direction.NORTH);
	}
}
