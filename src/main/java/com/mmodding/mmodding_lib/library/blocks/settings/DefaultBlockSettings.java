package com.mmodding.mmodding_lib.library.blocks.settings;

import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public enum DefaultBlockSettings {
	STONE_SETTINGS,
	DEEPSLATE_SETTINGS,
	BEDROCK_SETTINGS,
	DEEP_BEDROCK_SETTINGS,
	WOOD_SETTINGS,
	DIRT_SETTINGS,
	GRASS_SETTINGS,
	PLANT_SETTINGS,
	REPLACEABLE_PLANT_SETTINGS,
	LEAVES_SETTINGS,
	SNOW_SETTINGS,
	SNOW_BLOCK_SETTINGS,
	METAL_SETTINGS,
	AMETHYST_SETTINGS,
	AMETHYST_CLUSTER_SETTINGS,
	BONE_SETTINGS,
	GLASS_SETTINGS,
	SAND_SETTINGS,
	CLAY_SETTINGS,
	BASALT_SETTINGS,
	LODESTONE_SETTINGS,
	LEAVES_CARPET,
	NETHERITE_SETTINGS;

	public static QuiltBlockSettings ofDefault(DefaultBlockSettings defaultBlockSettings) {
		return switch (defaultBlockSettings) {
			case STONE_SETTINGS -> QuiltBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 6.0f).requiresTool();
			case DEEPSLATE_SETTINGS -> QuiltBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.DEEPSLATE).strength(3.0f, 6.0f).requiresTool();
			case BEDROCK_SETTINGS -> QuiltBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(-1.0F, 3600000.0F).dropsNothing().allowsSpawning((state, world, pos, type) -> false);
			case DEEP_BEDROCK_SETTINGS -> QuiltBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.DEEPSLATE).strength(-1.0F, 3600000.0F).dropsNothing().allowsSpawning((state, world, pos, type) -> false);
			case WOOD_SETTINGS -> QuiltBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f);
			case DIRT_SETTINGS -> QuiltBlockSettings.of(Material.SOIL).sounds(BlockSoundGroup.GRAVEL).strength(0.5f);
			case GRASS_SETTINGS -> QuiltBlockSettings.of(Material.SOIL).sounds(BlockSoundGroup.GRASS).strength(0.6f);
			case PLANT_SETTINGS -> QuiltBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.GRASS).noCollision().breakInstantly();
			case REPLACEABLE_PLANT_SETTINGS -> QuiltBlockSettings.of(Material.REPLACEABLE_PLANT).sounds(BlockSoundGroup.GRASS).noCollision().breakInstantly();
			case LEAVES_SETTINGS -> QuiltBlockSettings.of(Material.LEAVES).sounds(BlockSoundGroup.GRASS).nonOpaque().strength(0.2f);
			case SNOW_SETTINGS -> QuiltBlockSettings.of(Material.SNOW_LAYER).sounds(BlockSoundGroup.SNOW).nonOpaque().strength(0.1f);
			case SNOW_BLOCK_SETTINGS -> QuiltBlockSettings.of(Material.SNOW_BLOCK).sounds(BlockSoundGroup.SNOW).strength(0.1f);
			case METAL_SETTINGS -> QuiltBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5.0f, 6.0f);
			case AMETHYST_SETTINGS -> QuiltBlockSettings.of(Material.AMETHYST).sounds(BlockSoundGroup.AMETHYST_BLOCK).strength(1.5f).requiresTool();
			case AMETHYST_CLUSTER_SETTINGS -> QuiltBlockSettings.of(Material.AMETHYST).sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5f).requiresTool();
			case BONE_SETTINGS -> QuiltBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.BONE).strength(2.0f);
			case GLASS_SETTINGS -> QuiltBlockSettings.of(Material.GLASS).sounds(BlockSoundGroup.GLASS).nonOpaque().strength(0.3f);
			case SAND_SETTINGS -> QuiltBlockSettings.of(Material.AGGREGATE).sounds(BlockSoundGroup.SAND).strength(0.5f);
			case CLAY_SETTINGS -> QuiltBlockSettings.of(Material.ORGANIC_PRODUCT).sounds(BlockSoundGroup.SAND).strength(0.6f);
			case BASALT_SETTINGS -> QuiltBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.BASALT).strength(1.25f, 4.2f).requiresTool();
			case LODESTONE_SETTINGS -> QuiltBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.LODESTONE);
			case LEAVES_CARPET -> QuiltBlockSettings.of(Material.CARPET).sounds(BlockSoundGroup.GRASS).nonOpaque().strength(0.2f);
			case NETHERITE_SETTINGS -> QuiltBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.NETHERITE).strength(50.0f, 1200.0f);
		};
	}

	public static BlockSettingsModifiers ofDefaultForModifiers(DefaultBlockSettings defaultBlockSettings) {
		return BlockSettingsModifiers.ofSettings(DefaultBlockSettings.ofDefault(defaultBlockSettings));
	}
}
