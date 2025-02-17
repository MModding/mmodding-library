package com.mmodding.mmodding_lib.library.blocks.settings;

import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;

public class DefaultBlockSettings {

	public static final ImmutableBlockSettings STONE_SETTINGS = ImmutableBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 6.0f).requiresTool();
	public static final ImmutableBlockSettings DEEPSLATE_SETTINGS = ImmutableBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.DEEPSLATE).strength(3.0f, 6.0f).requiresTool();
	public static final ImmutableBlockSettings BEDROCK_SETTINGS = ImmutableBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(-1.0F, 3600000.0F).dropsNothing().allowsSpawning((state, world, pos, type) -> false);
	public static final ImmutableBlockSettings DEEP_BEDROCK_SETTINGS = ImmutableBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.DEEPSLATE).strength(-1.0F, 3600000.0F).dropsNothing().allowsSpawning((state, world, pos, type) -> false);
	public static final ImmutableBlockSettings WOOD_SETTINGS = ImmutableBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f);
	public static final ImmutableBlockSettings DIRT_SETTINGS = ImmutableBlockSettings.of(Material.SOIL).sounds(BlockSoundGroup.GRAVEL).strength(0.5f);
	public static final ImmutableBlockSettings GRASS_SETTINGS = ImmutableBlockSettings.of(Material.SOIL).sounds(BlockSoundGroup.GRASS).strength(0.6f);
	public static final ImmutableBlockSettings PLANT_SETTINGS = ImmutableBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS);
	public static final ImmutableBlockSettings SAPLING_SETTINGS = ImmutableBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.GRASS).noCollision().breakInstantly();
	public static final ImmutableBlockSettings REPLACEABLE_PLANT_SETTINGS = ImmutableBlockSettings.of(Material.REPLACEABLE_PLANT).sounds(BlockSoundGroup.GRASS).noCollision().breakInstantly();
	public static final ImmutableBlockSettings LEAVES_SETTINGS = ImmutableBlockSettings.of(Material.LEAVES).sounds(BlockSoundGroup.GRASS).nonOpaque().strength(0.2f);
	public static final ImmutableBlockSettings SNOW_SETTINGS = ImmutableBlockSettings.of(Material.SNOW_LAYER).sounds(BlockSoundGroup.SNOW).nonOpaque().strength(0.1f);
	public static final ImmutableBlockSettings SNOW_BLOCK_SETTINGS = ImmutableBlockSettings.of(Material.SNOW_BLOCK).sounds(BlockSoundGroup.SNOW).strength(0.1f);
	public static final ImmutableBlockSettings METAL_SETTINGS = ImmutableBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5.0f, 6.0f).requiresTool();
	public static final ImmutableBlockSettings AMETHYST_SETTINGS = ImmutableBlockSettings.of(Material.AMETHYST).sounds(BlockSoundGroup.AMETHYST_BLOCK).strength(1.5f).requiresTool();
	public static final ImmutableBlockSettings AMETHYST_CLUSTER_SETTINGS = ImmutableBlockSettings.of(Material.AMETHYST).sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5f).requiresTool();
	public static final ImmutableBlockSettings BONE_SETTINGS = ImmutableBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.BONE).strength(2.0f);
	public static final ImmutableBlockSettings GLASS_SETTINGS = ImmutableBlockSettings.of(Material.GLASS).sounds(BlockSoundGroup.GLASS).nonOpaque().strength(0.3f);
	public static final ImmutableBlockSettings SAND_SETTINGS = ImmutableBlockSettings.of(Material.AGGREGATE).sounds(BlockSoundGroup.SAND).strength(0.5f);
	public static final ImmutableBlockSettings CLAY_SETTINGS = ImmutableBlockSettings.of(Material.ORGANIC_PRODUCT).sounds(BlockSoundGroup.GRAVEL).strength(0.6f);
	public static final ImmutableBlockSettings BASALT_SETTINGS = ImmutableBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.BASALT).strength(1.25f, 4.2f).requiresTool();
	public static final ImmutableBlockSettings LODESTONE_SETTINGS = ImmutableBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.LODESTONE);
	public static final ImmutableBlockSettings LEAVES_CARPET = ImmutableBlockSettings.of(Material.CARPET).sounds(BlockSoundGroup.GRASS).nonOpaque().strength(0.2f);
	public static final ImmutableBlockSettings NETHERITE_SETTINGS = ImmutableBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.NETHERITE).strength(50.0f, 1200.0f);
}
