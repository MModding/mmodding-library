package com.mmodding.mmodding_lib.library.worldgen.features.defaults;

import net.minecraft.util.Holder;
import net.minecraft.util.HolderSet;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class CustomPointedDripstoneFeature implements CustomFeature, FeatureRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean();
	private final AtomicReference<Identifier> identifier = new AtomicReference<>();
	private final List<Pair<PlacedFeature, String>> additionalPlacedFeatures = new ArrayList<>();

	private final IntProvider countRange;
	private final IntProvider numberRange;
	private final IntProvider spreadXZ;
	private final IntProvider spreadY;
	private final DripstoneValue tallerDripstoneChance;
	private final DripstoneValue directionalSpreadChance;
	private final DripstoneValue spreadRadius2Chance;
	private final DripstoneValue spreadRadius3Chance;

	public CustomPointedDripstoneFeature(IntProvider countRange, IntProvider numberRange, IntProvider spreadXZ, IntProvider spreadY, float tallerDripstoneChance, float directionalSpreadChance, float spreadRadius2Chance, float spreadRadius3Chance) {
		this(
			countRange,
			numberRange,
			spreadXZ,
			spreadY,
			DripstoneValue.of(tallerDripstoneChance),
			DripstoneValue.of(directionalSpreadChance),
			DripstoneValue.of(spreadRadius2Chance),
			DripstoneValue.of(spreadRadius3Chance)
		);
	}

	public CustomPointedDripstoneFeature(IntProvider countRange, IntProvider numberRange, IntProvider spreadXZ, IntProvider spreadY, DripstoneValue tallerDripstoneChance, DripstoneValue directionalSpreadChance, DripstoneValue spreadRadius2Chance, DripstoneValue spreadRadius3Chance) {
		this.countRange = countRange;
		this.numberRange = numberRange;
		this.spreadXZ = spreadXZ;
		this.spreadY = spreadY;
		this.tallerDripstoneChance = tallerDripstoneChance;
		this.directionalSpreadChance = directionalSpreadChance;
		this.spreadRadius2Chance = spreadRadius2Chance;
		this.spreadRadius3Chance = spreadRadius3Chance;
	}

	@Override
	public Feature<SimpleRandomFeatureConfig> getFeature() {
		return Feature.SIMPLE_RANDOM_SELECTOR;
	}

	@Override
	public ConfiguredFeature<?, ?> getConfiguredFeature() {
		return new ConfiguredFeature<>(this.getFeature(), new SimpleRandomFeatureConfig(HolderSet.createDirect(
			PlacedFeatureUtil.placedInline(
				Feature.POINTED_DRIPSTONE,
				new PointedDripstoneFeatureConfig(
					this.tallerDripstoneChance.stalagmiteValue(),
					this.directionalSpreadChance.stalagmiteValue(),
					this.spreadRadius2Chance.stalagmiteValue(),
					this.spreadRadius3Chance.stalagmiteValue()
				),
				EnvironmentScanPlacementModifier.create(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR_OR_WATER, 12),
				RandomOffsetPlacementModifier.vertical(ConstantIntProvider.create(1))
			),
			PlacedFeatureUtil.placedInline(
				Feature.POINTED_DRIPSTONE,
				new PointedDripstoneFeatureConfig(
					this.tallerDripstoneChance.stalactiteValue(),
					this.directionalSpreadChance.stalactiteValue(),
					this.spreadRadius2Chance.stalactiteValue(),
					this.spreadRadius3Chance.stalactiteValue()
				),
				EnvironmentScanPlacementModifier.create(Direction.UP, BlockPredicate.solid(), BlockPredicate.IS_AIR_OR_WATER, 12),
				RandomOffsetPlacementModifier.vertical(ConstantIntProvider.create(-1))
			)
		)));
	}

	public PlacedFeature createPlacedFeature(IntProvider countRange, IntProvider numberRange, IntProvider spreadXZ, IntProvider spreadY) {

		List<PlacementModifier> placementModifiers = new ArrayList<>();
		placementModifiers.add(CountPlacementModifier.create(countRange));
		placementModifiers.add(InSquarePlacementModifier.getInstance());
		placementModifiers.add(PlacedFeatureUtil.BOTTOM_TO_MAX_TERRAIN_HEIGHT_RANGE);
		placementModifiers.add(CountPlacementModifier.create(numberRange));
		placementModifiers.add(RandomOffsetPlacementModifier.create(spreadXZ, spreadY));
		placementModifiers.add(BiomePlacementModifier.getInstance());

		return new PlacedFeature(Holder.createDirect(this.getConfiguredFeature()), placementModifiers);
	}

	@Override
	public PlacedFeature getDefaultPlacedFeature() {
		return this.createPlacedFeature(this.countRange, this.numberRange, this.spreadXZ, this.spreadY);
	}

	public CustomPointedDripstoneFeature addPlacedFeature(IntProvider countRange, IntProvider numberRange, IntProvider spreadXZ, IntProvider spreadY, String idExt) {
		this.additionalPlacedFeatures.add(new Pair<>(this.createPlacedFeature(countRange, numberRange, spreadXZ, spreadY), idExt));
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
				ctx, GenerationStep.Feature.UNDERGROUND_DECORATION,
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

	public record DripstoneValue(float stalagmiteValue, float stalactiteValue) {

		public static DripstoneValue of(float dripstoneValue) {
			return new DripstoneValue(dripstoneValue, dripstoneValue);
		}
	}
}
