package com.mmodding.mmodding_lib.library.worldgen.features.defaults;

import com.mmodding.mmodding_lib.library.utils.BiArrayList;
import com.mmodding.mmodding_lib.library.utils.BiList;
import com.mmodding.mmodding_lib.library.utils.IdentifierUtils;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectionContext;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class CustomBooleanFeature implements CustomFeature, FeatureRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean();
	private final AtomicReference<Identifier> identifier = new AtomicReference<>();
	private final BiList<PlacedFeature, String> additionalPlacedFeatures = new BiArrayList<>();

	private final RegistryKey<ConfiguredFeature<?, ?>> firstFeature;
	private final RegistryKey<ConfiguredFeature<?, ?>> lastFeature;
	private final GenerationStep.Feature step;
	private final List<PlacementModifier> defaultPlacementModifiers;

	public CustomBooleanFeature(RegistryKey<ConfiguredFeature<?, ?>> firstFeature, RegistryKey<ConfiguredFeature<?, ?>> lastFeature, GenerationStep.Feature step, PlacementModifier... defaultPlacementModifiers) {
		this.firstFeature = firstFeature;
		this.lastFeature = lastFeature;
		this.step = step;
		this.defaultPlacementModifiers = List.of(defaultPlacementModifiers);
	}

	@Override
	public Feature<RandomBooleanFeatureConfig> getFeature() {
		return Feature.RANDOM_BOOLEAN_SELECTOR;
	}

	@Override
	public ConfiguredFeature<?, ?> getConfiguredFeature() {
		Holder<ConfiguredFeature<?, ?>> firstConfiguredFeature = BuiltinRegistries.CONFIGURED_FEATURE.getHolderOrThrow(this.firstFeature);
		Holder<ConfiguredFeature<?, ?>> lastConfiguredFeature = BuiltinRegistries.CONFIGURED_FEATURE.getHolderOrThrow(this.lastFeature);
		return new ConfiguredFeature<>(this.getFeature(), new RandomBooleanFeatureConfig(
			PlacedFeatureUtil.placedInline(firstConfiguredFeature),
			PlacedFeatureUtil.placedInline(lastConfiguredFeature)
		));
	}

	public PlacedFeature createPlacedFeature(List<PlacementModifier> placementModifiers) {
		return new PlacedFeature(Holder.createDirect(this.getConfiguredFeature()), placementModifiers);
	}

	@Override
	public PlacedFeature getDefaultPlacedFeature() {
		return this.createPlacedFeature(this.defaultPlacementModifiers);
	}

	public CustomBooleanFeature addPlacedFeature(List<PlacementModifier> placementModifiers, String idExt) {
		this.additionalPlacedFeatures.add(this.createPlacedFeature(placementModifiers), idExt);
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
				ctx, this.step,
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
