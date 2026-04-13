package com.mmodding.library.worldgen.api.feature.catalog.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.PointedDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class AdvancedPointedDripstoneConfiguration extends PointedDripstoneConfiguration {

	public static final Codec<AdvancedPointedDripstoneConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		BlockStateProvider.CODEC.fieldOf("pointed_dripstone_block").forGetter(config -> config.pointedDripstoneBlock),
		BlockStateProvider.CODEC.fieldOf("dripstone_block").forGetter(config -> config.dripstoneBlock),
		Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_taller_dripstone").orElse(0.2F).forGetter(config -> config.chanceOfTallerDripstone),
		Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_directional_spread").orElse(0.7F).forGetter(config -> config.chanceOfDirectionalSpread),
		Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius2").orElse(0.5F).forGetter(config -> config.chanceOfSpreadRadius2),
		Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius3").orElse(0.5F).forGetter(config -> config.chanceOfSpreadRadius3)
	).apply(instance, AdvancedPointedDripstoneConfiguration::new));

	public final BlockStateProvider pointedDripstoneBlock;
	public final BlockStateProvider dripstoneBlock;

	public AdvancedPointedDripstoneConfiguration(BlockStateProvider pointedDripstoneBlock, BlockStateProvider dripstoneBlock, float tallerDripstoneChance, float directionalSpreadChance, float spreadRadius2Chance, float spreadRadius3Chance) {
		super(tallerDripstoneChance, directionalSpreadChance, spreadRadius2Chance, spreadRadius3Chance);
		this.pointedDripstoneBlock = pointedDripstoneBlock;
		this.dripstoneBlock = dripstoneBlock;
	}
}
