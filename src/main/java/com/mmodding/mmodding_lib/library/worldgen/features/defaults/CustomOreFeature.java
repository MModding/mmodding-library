package com.mmodding.mmodding_lib.library.worldgen.features.defaults;

import com.mmodding.mmodding_lib.library.utils.BiArrayList;
import com.mmodding.mmodding_lib.library.utils.BiList;
import com.mmodding.mmodding_lib.library.utils.IdentifierUtils;
import com.mmodding.mmodding_lib.library.utils.ListUtils;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class CustomOreFeature implements CustomFeature, FeatureRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean();
	private final AtomicReference<Identifier> identifier = new AtomicReference<>();
	private final BiList<PlacedFeature, String> additionalPlacedFeatures = new BiArrayList<>();

	private final int veinSize;
	private final int veinNumber;
	private final int minHeight;
	private final int maxHeight;
	private final List<OreFeatureConfig.Target> targets;
	private final float discardOnAirChance;

	public CustomOreFeature(int veinSize, int veinNumber, int minHeight, int maxHeight, List<OreFeatureConfig.Target> targets) {
		this(veinSize, veinNumber, minHeight, maxHeight, targets, 0.0f);
	}

	public CustomOreFeature(int veinSize, int veinNumber, int minHeight, int maxHeight, List<OreFeatureConfig.Target> targets, float discardOnAirChance) {
		this.veinSize = veinSize;
		this.veinNumber = veinNumber;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.targets = targets;
		this.discardOnAirChance = discardOnAirChance;
	}

	@Override
	public Feature<OreFeatureConfig> getFeature() {
		return Feature.ORE;
	}

	@Override
	public ConfiguredFeature<?, ?> getConfiguredFeature() {
		return new ConfiguredFeature<>(this.getFeature(), new OreFeatureConfig(this.targets, this.veinSize, this.discardOnAirChance));
	}

	public PlacedFeature createPlacedFeature(int veinNumber, int minHeight, int maxHeight) {
		return new PlacedFeature(Holder.createDirect(this.getConfiguredFeature()), ListUtils.builder(placementModifiers -> {
			placementModifiers.add(CountPlacementModifier.create(veinNumber));
			placementModifiers.add(InSquarePlacementModifier.getInstance());
			placementModifiers.add(HeightRangePlacementModifier.createUniform(YOffset.fixed(minHeight), YOffset.fixed(maxHeight)));
		}));
	}

	@Override
	public PlacedFeature getDefaultPlacedFeature() {
		return this.createPlacedFeature(this.veinNumber, this.minHeight, this.maxHeight);
	}

	public CustomOreFeature addPlacedFeature(int veinNumber, int minHeight, int maxHeight, String idExt) {
		this.additionalPlacedFeatures.add(this.createPlacedFeature(veinNumber, minHeight, maxHeight), idExt);
		return this;
	}

	@Override
	public BiList<PlacedFeature, String> getAdditionalPlacedFeatures() {
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
				ctx, GenerationStep.Feature.UNDERGROUND_ORES,
				RegistryKey.of(Registry.PLACED_FEATURE_KEY, IdentifierUtils.extend(this.identifier.get(), idExt))
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
