package com.mmodding.mmodding_lib.library.utils;

import com.mmodding.mmodding_lib.library.blocks.BlockWithItem;
import com.mmodding.mmodding_lib.library.worldgen.ChunkGeneratorRegistration;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;

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

	@Deprecated
	public static void registerBlockWithoutItem(Identifier identifier, Block block) {
		Registry.register(Registry.BLOCK, identifier, block);
	}

	public static <T extends Entity> void registerEntityType(Identifier identifier, EntityType<T> entityType) {
		Registry.register(Registry.ENTITY_TYPE, identifier, entityType);
	}

	public static void registerBiome(Identifier identifier, Biome biome) {
		BuiltinRegistries.register(BuiltinRegistries.BIOME, identifier, biome);
	}

	public static <C extends FeatureConfig, F extends Feature<C>> void registerFeature(Identifier identifier, Feature<C> feature, ConfiguredFeature<C, F> configuredFeature, PlacedFeature placedFeature) {
		Registry.register(Registry.FEATURE, identifier, feature);
		registerConfiguredFeature(identifier, configuredFeature);
		registerPlacedFeature(identifier, placedFeature);
	}

	@Deprecated
	public static <C extends FeatureConfig, F extends Feature<C>> void registerFeatureWithoutPlaced(Identifier identifier, Feature<C> feature, ConfiguredFeature<C, F> configuredFeature) {
		Registry.register(Registry.FEATURE, identifier, feature);
		registerConfiguredFeature(identifier, configuredFeature);
	}

	@Deprecated
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
		ChunkGeneratorRegistration.register(identifier, settings);
	}
}
