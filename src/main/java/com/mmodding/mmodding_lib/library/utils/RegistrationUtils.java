package com.mmodding.mmodding_lib.library.utils;

import com.mmodding.mmodding_lib.library.blocks.BlockWithItem;
import com.mmodding.mmodding_lib.library.portals.squared.CustomSquaredPortal;
import com.mmodding.mmodding_lib.library.portals.squared.UnlinkedCustomSquaredPortal;
import com.mmodding.mmodding_lib.library.stellar.StellarCycle;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.DensityFunction;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import net.minecraft.world.gen.root.RootPlacer;
import net.minecraft.world.gen.root.RootPlacerType;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import net.minecraft.world.poi.PointOfInterestType;

public class RegistrationUtils {

	public static void registerItem(Identifier identifier, Item item) {
		Registry.register(Registry.ITEM, identifier, item);
	}

	public static void registerPotion(Identifier identifier, Potion potion) {
		Registry.register(Registry.POTION, identifier, potion);
	}

	public static void registerEnchantment(Identifier identifier, Enchantment enchantment) {
		Registry.register(Registry.ENCHANTMENT, identifier, enchantment);
	}

	public static void registerBlock(Identifier identifier, BlockWithItem blockWithItem) {
		if (blockWithItem instanceof Block block) {
			Registry.register(Registry.BLOCK, identifier, block);
			if (blockWithItem.getItem() != null) registerItem(identifier, blockWithItem.getItem());
		}
	}

	@Deprecated
	public static void registerBlockAndItem(Identifier identifier, Block block, BlockItem blockItem) {
		Registry.register(Registry.BLOCK, identifier, block);
		Registry.register(Registry.ITEM, identifier, blockItem);
	}

	public static void registerBlockWithoutItem(Identifier identifier, Block block) {
		Registry.register(Registry.BLOCK, identifier, block);
	}

	public static void registerFluid(Identifier identifier, Fluid fluid) {
		Registry.register(Registry.FLUID, identifier, fluid);
	}

	public static <T extends Entity> void registerEntityType(Identifier identifier, EntityType<T> entityType) {
		Registry.register(Registry.ENTITY_TYPE, identifier, entityType);
	}

	public static <T extends BlockEntity> void registerBlockEntityType(Identifier identifier, BlockEntityType<T> blockEntityType) {
		Registry.register(Registry.BLOCK_ENTITY_TYPE, identifier, blockEntityType);
	}

	public static <T extends ScreenHandler> void registerScreenHandlerType(Identifier identifier, ScreenHandlerType<T> screenHandlerType) {
		Registry.register(Registry.SCREEN_HANDLER, identifier, screenHandlerType);
	}

	public static void registerBiome(Identifier identifier, Biome biome) {
		BuiltinRegistries.register(BuiltinRegistries.BIOME, identifier, biome);
	}

	public static <C extends FeatureConfig, F extends Feature<C>> void registerFeature(Identifier identifier, Feature<C> feature, ConfiguredFeature<C, F> configuredFeature, PlacedFeature placedFeature) {
		Registry.register(Registry.FEATURE, identifier, feature);
		registerConfiguredFeature(identifier, configuredFeature);
		registerPlacedFeature(identifier, placedFeature);
	}

	public static <C extends FeatureConfig, F extends Feature<C>> void registerFeatureWithoutPlaced(Identifier identifier, Feature<C> feature, ConfiguredFeature<C, F> configuredFeature) {
		Registry.register(Registry.FEATURE, identifier, feature);
		registerConfiguredFeature(identifier, configuredFeature);
	}

	public static <C extends FeatureConfig> void registerFeatureWithoutConfiguredAndPlaced(Identifier identifier, Feature<C> feature) {
		Registry.register(Registry.FEATURE, identifier, feature);
	}

	public static <C extends FeatureConfig, F extends Feature<C>> void registerConfiguredFeature(Identifier identifier, ConfiguredFeature<C, F> configuredFeature) {
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, identifier, configuredFeature);
	}

	public static void registerPlacedFeature(Identifier identifier, PlacedFeature placedFeature) {
		Registry.register(BuiltinRegistries.PLACED_FEATURE, identifier, placedFeature);
	}

	public static void registerChunkGeneratorSettings(Identifier identifier, ChunkGeneratorSettings settings) {
		Registry.register(BuiltinRegistries.CHUNK_GENERATOR_SETTINGS, identifier, settings);
	}

	public static void registerDensityFunction(Identifier identifier, DensityFunction densityFunction) {
		Registry.register(BuiltinRegistries.DENSITY_FUNCTION, identifier, densityFunction);
	}

	public static <P extends TrunkPlacer> void registerTrunkPlacerType(Identifier identifier, TrunkPlacerType<P> trunkPlacerType) {
		Registry.register(Registry.TRUNK_PLACER_TYPE, identifier, trunkPlacerType);
	}

	public static <P extends FoliagePlacer> void registerFoliagePlacerType(Identifier identifier, FoliagePlacerType<P> foliagePlacerType) {
		Registry.register(Registry.FOLIAGE_PLACER_TYPE, identifier, foliagePlacerType);
	}

	public static <P extends TreeDecorator> void registerTreeDecoratorType(Identifier identifier, TreeDecoratorType<P> treeDecoratorType) {
		Registry.register(Registry.TREE_DECORATOR_TYPE, identifier, treeDecoratorType);
	}

	public static <P extends RootPlacer> void registerRootPlacerType(Identifier identifier, RootPlacerType<P> rootPlacerType) {
		Registry.register(Registry.ROOT_PLACER_TYPE, identifier, rootPlacerType);
	}

	public static void registerPointOfInterestType(Identifier identifier, PointOfInterestType type) {
		Registry.register(Registry.POINT_OF_INTEREST_TYPE, identifier, type);
	}

	public static void registerSoundEvent(SoundEvent soundEvent) {
		RegistrationUtils.registerSoundEvent(soundEvent.getId(), soundEvent);
	}

	public static void registerSoundEvent(Identifier identifier, SoundEvent soundEvent) {
		Registry.register(Registry.SOUND_EVENT, identifier, soundEvent);
	}

	public static void registerStellarCycle(Identifier identifier, StellarCycle stellarCycle) {
		MModdingGlobalMaps.STELLAR_CYCLES.put(identifier, stellarCycle);
	}

	public static void registerSquaredPortal(Identifier identifier, CustomSquaredPortal squaredPortal) {
		MModdingGlobalMaps.CUSTOM_SQUARED_PORTALS.put(identifier, squaredPortal);
	}

	public static void registerUnlinkedSquaredPortal(Identifier identifier, UnlinkedCustomSquaredPortal squaredPortal) {
		MModdingGlobalMaps.UNLINKED_CUSTOM_SQUARED_PORTALS.put(identifier, squaredPortal);
	}
}
