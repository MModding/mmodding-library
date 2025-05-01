package com.mmodding.mmodding_lib.library.worldgen.features.defaults;

import com.mmodding.mmodding_lib.library.utils.BiArrayList;
import com.mmodding.mmodding_lib.library.utils.BiList;
import com.mmodding.mmodding_lib.library.utils.IdentifierUtils;
import com.mmodding.mmodding_lib.library.utils.ListUtils;
import com.mmodding.mmodding_lib.library.worldgen.MModdingFeatures;
import com.mmodding.mmodding_lib.library.worldgen.features.builtin.differed.DifferedFreezeTopLayerFeature;
import net.minecraft.block.Block;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.BiomeSelectionContext;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class CustomFreezeTopLayerFeature implements CustomFeature, FeatureRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean();
	private final AtomicReference<Identifier> identifier = new AtomicReference<>();
	private final BiList<PlacedFeature, String> additionalPlacedFeatures = new BiArrayList<>();

	private final Block iceBlock;
	private final Block snowLayer;
	private final int depthCoverage;

	public CustomFreezeTopLayerFeature(Block iceBlock, Block snowLayer) {
		this(iceBlock, snowLayer, 1);
	}

	public CustomFreezeTopLayerFeature(Block iceBlock, Block snowLayer, int depthCoverage) {
		this.iceBlock = iceBlock;
		this.snowLayer = snowLayer;
		this.depthCoverage = depthCoverage;
	}

	@Override
	public Feature<DifferedFreezeTopLayerFeature.Config> getFeature() {
		return MModdingFeatures.DIFFERED_FREEZE_TOP_LAYER;
	}

	@Override
	public ConfiguredFeature<?, ?> getConfiguredFeature() {
		return new ConfiguredFeature<>(this.getFeature(), new DifferedFreezeTopLayerFeature.Config(
			BlockStateProvider.of(this.iceBlock),
			BlockStateProvider.of(this.snowLayer),
			ConstantIntProvider.create(this.depthCoverage)
		));
	}

	public PlacedFeature createPlacedFeature() {
		return new PlacedFeature(
			Holder.createDirect(this.getConfiguredFeature()),
			ListUtils.builder(placementModifiers -> placementModifiers.add(BiomePlacementModifier.getInstance()))
		);
	}

	@Override
	public PlacedFeature getDefaultPlacedFeature() {
		return this.createPlacedFeature();
	}

	public CustomFreezeTopLayerFeature addPlacedFeature(String idExt) {
		this.additionalPlacedFeatures.add(this.createPlacedFeature(), idExt);
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
				ctx, GenerationStep.Feature.TOP_LAYER_MODIFICATION,
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
