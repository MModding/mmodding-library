package com.mmodding.mmodding_lib.library.utils;

import net.fabricmc.fabric.api.util.BooleanFunction;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.Holder;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public class SaplingGeneratorUtils {

    public static SaplingGenerator of(Holder<? extends ConfiguredFeature<?, ?>> treeConfiguredFeature) {
        return SaplingGeneratorUtils.of(value -> treeConfiguredFeature);
    }

    public static SaplingGenerator of(BooleanFunction<Holder<? extends ConfiguredFeature<?, ?>>> function) {
        return SaplingGeneratorUtils.of((random, value) -> function.apply(value));
    }

    public static SaplingGenerator of(BiFunction<RandomGenerator, Boolean, Holder<? extends ConfiguredFeature<?, ?>>> biFunction) {

        return new SaplingGenerator() {

            @Nullable
            @Override
            protected Holder<? extends ConfiguredFeature<?, ?>> getTreeFeature(RandomGenerator random, boolean bees) {
                return biFunction.apply(random, bees);
            }
        };
    }
}
