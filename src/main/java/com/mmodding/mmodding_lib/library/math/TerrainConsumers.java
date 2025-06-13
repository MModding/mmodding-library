package com.mmodding.mmodding_lib.library.math;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.function.Consumer;

public class TerrainConsumers {

	public static OrientedConsumer oriented(BlockPos basePos, Consumer<BlockPos> consumer) {
		return new OrientedConsumer(basePos, consumer);
	}

	public static NonOrientedConsumer nonOriented(BlockPos basePos, Consumer<OrientedBlockPos> consumer) {
		return new NonOrientedConsumer(basePos, consumer);
	}

    public static class OrientedConsumer {

        private final BlockPos basePos;
        private final Consumer<BlockPos> consumer;

        private OrientedConsumer(BlockPos basePos, Consumer<BlockPos> consumer) {
            this.basePos = basePos;
            this.consumer = consumer;
        }

        public void apply() {
            this.consumer.accept(this.basePos);
        }
    }

    public static class NonOrientedConsumer {

        private final BlockPos basePos;
        private final Consumer<OrientedBlockPos> consumer;

        private NonOrientedConsumer(BlockPos basePos, Consumer<OrientedBlockPos> consumer) {
            this.basePos = basePos;
            this.consumer = consumer;
        }

        public void apply(Direction direction) {
            this.consumer.accept(OrientedBlockPos.of(this.basePos).apply(direction));
        }
    }
}
