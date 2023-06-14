package com.mmodding.mmodding_lib.library.blocks;

import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

public class DefaultBlockSettings {

	public static final QuiltBlockSettings STONE_SETTINGS = QuiltBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.STONE).strength(1.5f, 6.0f).requiresTool();
	public static final QuiltBlockSettings DEEPSLATE_SETTINGS = QuiltBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.DEEPSLATE).strength(3.0f, 6.0f).requiresTool();
	public static final QuiltBlockSettings WOOD_SETTINGS = QuiltBlockSettings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(2.0f);
	public static final QuiltBlockSettings DIRT_SETTINGS = QuiltBlockSettings.of(Material.SOIL).sounds(BlockSoundGroup.GRAVEL).strength(0.5f);
	public static final QuiltBlockSettings GRASS_SETTINGS = QuiltBlockSettings.of(Material.SOIL).sounds(BlockSoundGroup.GRASS).strength(0.6f);
	public static final QuiltBlockSettings PLANT_SETTINGS = QuiltBlockSettings.of(Material.PLANT).sounds(BlockSoundGroup.GRASS).noCollision().breakInstantly();
	public static final QuiltBlockSettings REPLACEABLE_PLANT_SETTINGS = QuiltBlockSettings.of(Material.REPLACEABLE_PLANT).sounds(BlockSoundGroup.GRASS).noCollision().breakInstantly();
	public static final QuiltBlockSettings LEAVES_SETTINGS = QuiltBlockSettings.of(Material.LEAVES).sounds(BlockSoundGroup.GRASS).nonOpaque().strength(0.2f);
	public static final QuiltBlockSettings SNOW_SETTINGS = QuiltBlockSettings.of(Material.SNOW_LAYER).sounds(BlockSoundGroup.SNOW).nonOpaque().strength(0.1f);
	public static final QuiltBlockSettings SNOW_BLOCK_SETTINGS = QuiltBlockSettings.of(Material.SNOW_BLOCK).sounds(BlockSoundGroup.SNOW).strength(0.1f);
	public static final QuiltBlockSettings METAL_SETTINGS = QuiltBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.METAL).strength(5.0f, 6.0f);
	public static final QuiltBlockSettings BONE_SETTINGS = QuiltBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.BONE).strength(2.0f);
	public static final QuiltBlockSettings GLASS_SETTINGS = QuiltBlockSettings.of(Material.GLASS).sounds(BlockSoundGroup.GLASS).nonOpaque().strength(0.3f);
	public static final QuiltBlockSettings SAND_SETTINGS = QuiltBlockSettings.of(Material.AGGREGATE).sounds(BlockSoundGroup.SAND).strength(0.5f);
	public static final QuiltBlockSettings CLAY_SETTINGS = QuiltBlockSettings.of(Material.ORGANIC_PRODUCT).sounds(BlockSoundGroup.SAND).strength(0.6f);
	public static final QuiltBlockSettings BASALT_SETTINGS = QuiltBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.BASALT).strength(1.25f, 4.2f);
	public static final QuiltBlockSettings LODESTONE_SETTINGS = QuiltBlockSettings.of(Material.STONE).sounds(BlockSoundGroup.LODESTONE);
	public static final QuiltBlockSettings LEAVES_CARPET = QuiltBlockSettings.of(Material.CARPET).sounds(BlockSoundGroup.GRASS).nonOpaque().strength(0.2f);
	public static final QuiltBlockSettings NETHERITE_SETTINGS = QuiltBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.NETHERITE).strength(50.0f, 1200.0f);
}
