package com.mmodding.mmodding_lib.library.utils;

import com.mmodding.mmodding_lib.library.worldgen.features.defaults.CustomTreeFeature;
import net.fabricmc.fabric.api.util.BooleanFunction;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.Holder;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public class SaplingGeneratorUtils {

	public static SaplingGenerator of(CustomTreeFeature treeFeature) {
		return SaplingGeneratorUtils.of(value -> treeFeature);
	}

	public static SaplingGenerator of(BooleanFunction<CustomTreeFeature> function) {
		return SaplingGeneratorUtils.of((random, value) -> function.apply(value));
	}

	public static SaplingGenerator of(BiFunction<RandomGenerator, Boolean, CustomTreeFeature> biFunction) {
		return SaplingGeneratorUtils.ofConfigured((random, value) -> biFunction.apply(random, value).getConfiguredFeature());
	}

	public static SaplingGenerator ofConfigured(ConfiguredFeature<?, ?> treeFeature) {
		return SaplingGeneratorUtils.ofConfigured(value -> treeFeature);
	}

	public static SaplingGenerator ofConfigured(BooleanFunction<ConfiguredFeature<?, ?>> function) {
		return SaplingGeneratorUtils.ofConfigured((random, value) -> function.apply(value));
	}

	public static SaplingGenerator ofConfigured(BiFunction<RandomGenerator, Boolean, ConfiguredFeature<?, ?>> biFunction) {
		return SaplingGeneratorUtils.ofHolder((random, value) -> Holder.createDirect(biFunction.apply(random, value)));
	}

    public static SaplingGenerator ofHolder(Holder<? extends ConfiguredFeature<?, ?>> treeConfiguredFeature) {
        return SaplingGeneratorUtils.ofHolder(value -> treeConfiguredFeature);
    }

    public static SaplingGenerator ofHolder(BooleanFunction<Holder<? extends ConfiguredFeature<?, ?>>> function) {
        return SaplingGeneratorUtils.ofHolder((random, value) -> function.apply(value));
    }

    public static SaplingGenerator ofHolder(BiFunction<RandomGenerator, Boolean, Holder<? extends ConfiguredFeature<?, ?>>> biFunction) {

        return new SaplingGenerator() {

            @Nullable
            @Override
            protected Holder<? extends ConfiguredFeature<?, ?>> getTreeFeature(RandomGenerator random, boolean bees) {
                return biFunction.apply(random, bees);
            }
        };
    }
}
