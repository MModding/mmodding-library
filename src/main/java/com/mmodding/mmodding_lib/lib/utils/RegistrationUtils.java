package com.mmodding.mmodding_lib.lib.utils;

import com.mmodding.mmodding_lib.lib.blocks.CustomBlock;
import com.mmodding.mmodding_lib.lib.structures.CustomStructure;
import com.mmodding.mmodding_lib.mixin.StructureFeatureAccessor;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.GenerationStep;

public class RegistrationUtils {
	public static void registerItem(Identifier identifier, Item item) {
		Registry.register(Registry.ITEM, identifier, item);
	}

	public static void registerBlock(Identifier identifier, CustomBlock block) {
		Registry.register(Registry.BLOCK, identifier, block);
		if (block.getItem() != null) registerItem(identifier, block.getItem());
	}

	public static void registerNormalBlock(Identifier identifier, Block block, BlockItem blockItem) {
		Registry.register(Registry.BLOCK, identifier, block);
		Registry.register(Registry.ITEM, identifier, blockItem);
	}

	public static <T extends Entity> void registerEntityType(Identifier identifier, EntityType<T> entityType) {
		Registry.register(Registry.ENTITY_TYPE, identifier, entityType);
	}

	public static void registerStructure(Identifier identifier, CustomStructure<?> structure) {
		StructureFeatureAccessor.callRegister(identifier.toString(), structure, GenerationStep.Feature.SURFACE_STRUCTURES);
	}
}
