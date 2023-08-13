package com.mmodding.mmodding_lib.library.math;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.function.Consumer;

public class TerrainConsumers {

    public static class Oriented {

        private final BlockPos basePos;
        private final Consumer<BlockPos> consumer;

        public Oriented(BlockPos basePos, Consumer<BlockPos> consumer) {
            this.basePos = basePos;
            this.consumer = consumer;
        }

        public void apply() {
            this.consumer.accept(this.basePos);
        }
    }

    public static class UnOriented {

        private final BlockPos basePos;
        private final Direction direction;
        private final Consumer<OrientedBlockPos> consumer;

        public UnOriented(BlockPos basePos, Direction direction, Consumer<OrientedBlockPos> consumer) {
            this.basePos = basePos;
            this.direction = direction;
            this.consumer = consumer;
        }

        public void apply() {
            this.consumer.accept(OrientedBlockPos.of(this.basePos).apply(this.direction));
        }
    }
}
