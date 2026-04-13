package com.mmodding.library.worldgen.api.feature.catalog.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class AdvancedLiquidVegetationPatchConfiguration extends VegetationPatchConfiguration {

	public static final Codec<AdvancedLiquidVegetationPatchConfiguration> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
				TagKey.hashedCodec(Registries.BLOCK).fieldOf("replaceable").forGetter(config -> config.replaceable),
				BlockStateProvider.CODEC.fieldOf("ground_state").forGetter(config -> config.groundState),
				BlockStateProvider.CODEC.fieldOf("liquid_state").forGetter(config -> config.liquidState),
				PlacedFeature.CODEC.fieldOf("vegetation_feature").forGetter(config -> config.vegetationFeature),
				CaveSurface.CODEC.fieldOf("surface").forGetter(config -> config.surface),
				IntProviders.codec(1, 128).fieldOf("depth").forGetter(config -> config.depth),
				Codec.floatRange(0.0f, 1.0f).fieldOf("extra_bottom_block_chance").forGetter(config -> config.extraBottomBlockChance),
				Codec.intRange(1, 256).fieldOf("vertical_range").forGetter(config -> config.verticalRange),
				Codec.floatRange(0.0f, 1.0f).fieldOf("vegetation_chance").forGetter(config -> config.vegetationChance),
				IntProviders.CODEC.fieldOf("xz_radius").forGetter(config -> config.xzRadius),
				Codec.floatRange(0.0f, 1.0f).fieldOf("extra_edge_column_chance").forGetter(config -> config.extraEdgeColumnChance)
			)
			.apply(instance, AdvancedLiquidVegetationPatchConfiguration::new)
	);

	public final BlockStateProvider liquidState;

	public AdvancedLiquidVegetationPatchConfiguration(TagKey<Block> replaceable, BlockStateProvider groundState, BlockStateProvider liquidState, Holder<PlacedFeature> vegetationFeature, CaveSurface surface, IntProvider depth, float extraBottomBlockChance, int verticalRange, float vegetationChance, IntProvider horizontalRadius, float extraEdgeColumnChance) {
		super(replaceable, groundState, vegetationFeature, surface, depth, extraBottomBlockChance, verticalRange, vegetationChance, horizontalRadius, extraEdgeColumnChance);
		this.liquidState = liquidState;
	}
}
