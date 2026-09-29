package com.mmodding.library.levelgen.api.feature.catalog;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.codec.RegistryCodecs;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.WaterloggedVegetationPatchFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

/**
 * Feature variant of the vanilla one with <code>WATER</code> constants modified through mixins.
 */
public class AdvancedLiquidVegetationPatchFeature extends WaterloggedVegetationPatchFeature {

	public static final MapCodec<AdvancedLiquidVegetationPatchFeature> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
		BlockStateProvider.CODEC.fieldOf("liquid_state").forGetter(f -> f.liquidState),
		RegistryCodecs.holderSet(Registries.BLOCK).fieldOf("replaceable").forGetter(f -> f.replaceable),
		BlockStateProvider.CODEC.fieldOf("ground_state").forGetter(f -> f.groundState),
		PlacedFeature.CODEC.fieldOf("vegetation_feature").forGetter(f -> f.vegetationFeature),
		CaveSurface.CODEC.fieldOf("surface").forGetter(f -> f.surface),
		IntProviders.codec(1, 128).fieldOf("depth").forGetter(f -> f.depth),
		Codec.floatRange(0.0F, 1.0F).fieldOf("extra_bottom_block_chance").forGetter(f -> f.extraBottomBlockChance),
		Codec.intRange(1, 256).fieldOf("vertical_range").forGetter(f -> f.verticalRange),
		Codec.floatRange(0.0F, 1.0F).fieldOf("vegetation_chance").forGetter(f -> f.vegetationChance),
		IntProviders.CODEC.fieldOf("xz_radius").forGetter(f -> f.xzRadius),
		Codec.floatRange(0.0F, 1.0F).fieldOf("extra_edge_column_chance").forGetter(f -> f.extraEdgeColumnChance)
	).apply(i, AdvancedLiquidVegetationPatchFeature::new));

	public final Holder<BlockStateProvider> liquidState;

	public AdvancedLiquidVegetationPatchFeature(Holder<BlockStateProvider> liquidState, HolderSet<Block> replaceable, Holder<BlockStateProvider> groundState, Holder<PlacedFeature> vegetationFeature, CaveSurface surface, IntProvider depth, float extraBottomBlockChance, int verticalRange, float vegetationChance, IntProvider xzRadius, float extraEdgeColumnChance) {
		super(replaceable, groundState, vegetationFeature, surface, depth, extraBottomBlockChance, verticalRange, vegetationChance, xzRadius, extraEdgeColumnChance);
		this.liquidState = liquidState;
	}
}
