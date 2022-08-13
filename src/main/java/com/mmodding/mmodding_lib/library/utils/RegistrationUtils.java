package com.mmodding.mmodding_lib.library.utils;

import com.mmodding.mmodding_lib.library.blocks.BlockWithItem;
import com.mmodding.mmodding_lib.library.structures.CustomStructure;
import com.mmodding.mmodding_lib.mixin.StructureFeatureAccessor;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;

public class RegistrationUtils {
	public static void registerItem(Identifier identifier, Item item) {
		Registry.register(Registry.ITEM, identifier, item);
	}

	public static void registerBlock(Identifier identifier, BlockWithItem blockWithItem) {
		if (blockWithItem instanceof Block block) {
			Registry.register(Registry.BLOCK, identifier, block);
			if (blockWithItem.getItem() != null) registerItem(identifier, blockWithItem.getItem());
		}
	}

	public static void registerNormalBlock(Identifier identifier, Block block, BlockItem blockItem) {
		Registry.register(Registry.BLOCK, identifier, block);
		Registry.register(Registry.ITEM, identifier, blockItem);
	}

	public static void registerBlockEntity(Identifier identifier, BlockEntity blockEntity) {
		/*
		Registry.register(Registry.BLOCK_ENTITY_TYPE, identifier,
			FabricBlockEntityTypeBuilder.create(DemoBlockEntity::new, DEMO_BLOCK).build());
		*/
	}

	public static <T extends Entity> void registerEntityType(Identifier identifier, EntityType<T> entityType) {
		Registry.register(Registry.ENTITY_TYPE, identifier, entityType);
	}

	public static void registerStructure(Identifier identifier, CustomStructure<?> structure) {
		StructureFeatureAccessor.callRegister(identifier.toString(), structure, GenerationStep.Feature.SURFACE_STRUCTURES);
	}

	public static void registerBiome(Identifier identifier, Biome biome) {
		Registry.register(BuiltinRegistries.BIOME, RegistryKey.of(Registry.BIOME_KEY, identifier), biome);
	}
}
