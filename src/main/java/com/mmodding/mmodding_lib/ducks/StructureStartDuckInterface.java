package com.mmodding.mmodding_lib.ducks;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface StructureStartDuckInterface {

	void mmodding_lib$provideCollectors(Consumer<BlockPos> structureContainersCollector, BiConsumer<Identifier, BlockPos> structurePieceContainersCollector);
}
