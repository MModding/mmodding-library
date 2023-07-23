package com.mmodding.mmodding_lib.library.worldgen.features.defaults;

import com.mmodding.mmodding_lib.library.worldgen.MModdingFeatures;
import com.mmodding.mmodding_lib.library.worldgen.features.differeds.DifferedLargeDripstoneFeature;
import net.minecraft.block.Block;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.BiomePlacementModifier;
import net.minecraft.world.gen.decorator.CountPlacementModifier;
import net.minecraft.world.gen.decorator.InSquarePlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class CustomLargeDripstoneFeature implements CustomFeature, FeatureRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean();
	private final AtomicReference<Identifier> identifier = new AtomicReference<>();
	private final List<Pair<PlacedFeature, String>> additionalPlacedFeatures = new ArrayList<>();

	private final Block dripstoneBlock;
	private final IntProvider countRange;
	private final int floorToCeilingSearchRange;
	public final IntProvider columnRadius;
	public final FloatProvider heightScale;
	public final float maxColumnRadiusToCaveHeightRatio;
	public final FloatProvider stalactiteBluntness;
	public final FloatProvider stalagmiteBluntness;
	public final FloatProvider windSpeed;
	public final int minRadiusForWind;
	public final float minBluntnessForWind;

	public CustomLargeDripstoneFeature(Block dripstoneBlock, IntProvider countRange, int floorToCeilingSearchRange, IntProvider columnRadius, FloatProvider heightScale, float maxColumnRadiusToCaveHeightRatio, FloatProvider stalactiteBluntness, FloatProvider stalagmiteBluntness, FloatProvider windSpeed, int minRadiusForWind, float minBluntnessForWind) {
		this.dripstoneBlock = dripstoneBlock;
		this.countRange = countRange;
		this.floorToCeilingSearchRange = floorToCeilingSearchRange;
		this.columnRadius = columnRadius;
		this.heightScale = heightScale;
		this.maxColumnRadiusToCaveHeightRatio = maxColumnRadiusToCaveHeightRatio;
		this.stalactiteBluntness = stalactiteBluntness;
		this.stalagmiteBluntness = stalagmiteBluntness;
		this.windSpeed = windSpeed;
		this.minRadiusForWind = minRadiusForWind;
		this.minBluntnessForWind = minBluntnessForWind;
	}

	@Override
	public Feature<DifferedLargeDripstoneFeature.Config> getFeature() {
		return MModdingFeatures.DIFFERED_LARGE_DRIPSTONE;
	}

	@Override
	public ConfiguredFeature<?, ?> getConfiguredFeature() {
		return new ConfiguredFeature<>(this.getFeature(), new DifferedLargeDripstoneFeature.Config(
			BlockStateProvider.of(this.dripstoneBlock),
			this.floorToCeilingSearchRange,
			this.columnRadius,
			this.heightScale,
			this.maxColumnRadiusToCaveHeightRatio,
			this.stalactiteBluntness,
			this.stalagmiteBluntness,
			this.windSpeed,
			this.minRadiusForWind,
			this.minBluntnessForWind
		));
	}

	public PlacedFeature createPlacedFeature(IntProvider countRange) {

		List<PlacementModifier> placementModifiers = new ArrayList<>();
		placementModifiers.add(CountPlacementModifier.create(countRange));
		placementModifiers.add(InSquarePlacementModifier.getInstance());
		placementModifiers.add(PlacedFeatureUtil.BOTTOM_TO_MAX_TERRAIN_HEIGHT_RANGE);
		placementModifiers.add(BiomePlacementModifier.getInstance());

		return new PlacedFeature(Holder.createDirect(this.getConfiguredFeature()), placementModifiers);
	}

	@Override
	public PlacedFeature getDefaultPlacedFeature() {
		return this.createPlacedFeature(this.countRange);
	}

	public CustomLargeDripstoneFeature addPlacedFeature(IntProvider countRange, String idExt) {
		this.additionalPlacedFeatures.add(new Pair<>(this.createPlacedFeature(countRange), idExt));
		return this;
	}

	@Override
	public List<Pair<PlacedFeature, String>> getAdditionalPlacedFeatures() {
		return this.additionalPlacedFeatures;
	}

	@Override
	public void addDefaultToBiomes(Predicate<BiomeSelectionContext> ctx) {
		this.addAdditionalToBiomes(ctx, "");
	}

	@Override
	public void addAdditionalToBiomes(Predicate<BiomeSelectionContext> ctx, String idExt) {
		if (this.registered.get()) {
			BiomeModifications.addFeature(
				ctx, GenerationStep.Feature.LOCAL_MODIFICATIONS,
				RegistryKey.of(Registry.PLACED_FEATURE_KEY, this.addIdExt(this.identifier.get(), idExt))
			);
		}
	}

	@Override
	public void setIdentifier(Identifier identifier) {
		this.identifier.set(identifier);
	}

	@Override
	public boolean isNotRegistered() {
		return !this.registered.get();
	}

	@Override
	public void setRegistered() {
		this.registered.set(true);
	}
}
