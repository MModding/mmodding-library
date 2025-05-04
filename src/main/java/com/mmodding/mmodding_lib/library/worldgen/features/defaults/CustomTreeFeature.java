package com.mmodding.mmodding_lib.library.worldgen.features.defaults;

import com.mmodding.mmodding_lib.library.utils.BiArrayList;
import com.mmodding.mmodding_lib.library.utils.BiList;
import com.mmodding.mmodding_lib.library.utils.IdentifierUtils;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.root.RootPlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.trunk.TrunkPlacer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public class CustomTreeFeature implements CustomFeature, FeatureRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean();
	private final AtomicReference<Identifier> identifier = new AtomicReference<>();
	private final BiList<PlacedFeature, String> additionalPlacedFeatures = new BiArrayList<>();

	private final AtomicReference<Block> groundBlock = new AtomicReference<>(Blocks.DIRT);
	private final List<TreeDecorator> treeDecorator = new ArrayList<>();
	private final Block trunkBlock;
	private final TrunkPlacer trunkPlacer;
	private final Block foliageBlock;
	private final FoliagePlacer foliagePlacer;
	private final Optional<RootPlacer> rootPlacer;
	private final int heightLimit;
	private final int minSize;
	private final int maxSize;
	private final PlacementModifier modifier;
	private final Block blockWouldSurvive;

	public CustomTreeFeature(Block trunkBlock, TrunkPlacer trunkPlacer, Block foliageBlock, FoliagePlacer foliagePlacer, int heightLimit, int minSize, int maxSize, PlacementModifier modifier, Block blockWouldSurvive) {
		this(trunkBlock, trunkPlacer, foliageBlock, foliagePlacer, Optional.empty(), heightLimit, minSize, maxSize, modifier, blockWouldSurvive);
	}

	public CustomTreeFeature(Block trunkBlock, TrunkPlacer trunkPlacer, Block foliageBlock, FoliagePlacer foliagePlacer, Optional<RootPlacer> rootPlacer, int heightLimit, int minSize, int maxSize, PlacementModifier modifier, Block blockWouldSurvive) {
		this.trunkBlock = trunkBlock;
		this.trunkPlacer = trunkPlacer;
		this.foliageBlock = foliageBlock;
		this.foliagePlacer = foliagePlacer;
		this.rootPlacer = rootPlacer;
		this.heightLimit = heightLimit;
		this.minSize = minSize;
		this.maxSize = maxSize;
		this.modifier = modifier;
		this.blockWouldSurvive = blockWouldSurvive;
	}

	@Override
	public Feature<TreeFeatureConfig> getFeature() {
		return Feature.TREE;
	}

	@Override
	public ConfiguredFeature<?, ?> getConfiguredFeature() {

		TreeFeatureConfig.Builder builder = new TreeFeatureConfig.Builder(
			BlockStateProvider.of(this.trunkBlock),
			this.trunkPlacer,
			BlockStateProvider.of(this.foliageBlock),
			this.foliagePlacer,
			this.rootPlacer,
			new TwoLayersFeatureSize(this.heightLimit, this.minSize, this.maxSize)
		);

		if (!this.treeDecorator.isEmpty()) builder.decorators(this.treeDecorator);

		return new ConfiguredFeature<>(this.getFeature(), builder.dirtProvider(BlockStateProvider.of(this.groundBlock.get())).build());
	}

	public CustomTreeFeature setGroundBlock(Block block) {
		this.groundBlock.set(block);
		return this;
	}

	public CustomTreeFeature addTreeDecorators(TreeDecorator... treeDecorator) {
		this.treeDecorator.addAll(List.of(treeDecorator));
		return this;
	}

	public PlacedFeature createPlacedFeature(PlacementModifier modifier, Block blockWouldSurvive) {
		return new PlacedFeature(Holder.createDirect(this.getConfiguredFeature()), VegetationPlacedFeatures.treePlacementModifiers(modifier, blockWouldSurvive));
	}

	@Override
	public PlacedFeature getDefaultPlacedFeature() {
		return this.createPlacedFeature(this.modifier, this.blockWouldSurvive);
	}

	public CustomTreeFeature addPlacedFeature(PlacementModifier modifier, Block blockWouldSurvive, String idExt) {
		this.additionalPlacedFeatures.add(this.createPlacedFeature(modifier, blockWouldSurvive), idExt);
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
				ctx, GenerationStep.Feature.VEGETAL_DECORATION,
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
