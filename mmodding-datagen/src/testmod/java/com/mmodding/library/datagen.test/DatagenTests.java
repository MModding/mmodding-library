package com.mmodding.library.datagen.test;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.core.api.ExtendedModInitializer;
import com.mmodding.library.datagen.api.ExtendedDataGeneratorEntrypoint;
import com.mmodding.library.datagen.api.lang.DefaultLangProcessors;
import com.mmodding.library.datagen.api.loot.block.BlockLootProcessor;
import com.mmodding.library.datagen.api.management.DataManager;
import com.mmodding.library.datagen.api.management.DefaultContentTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class DatagenTests implements ExtendedModInitializer, ExtendedDataGeneratorEntrypoint {

	public static final Block BLOCK = new Block(FabricBlockSettings.create().mapColor(MapColor.BLACK))
		.loot(BlockLootProcessor.standard());

	public static final EntityType<CowEntity> COW = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CowEntity::new).build()
		.loot(entityType -> LootTable.builder());

	@Override
	public void setupManager(ElementsManager manager) {
		manager.content(DatagenTests::register);
	}

	@Override
	public void setupManager(DataManager manager) {
		manager.data(DatagenTests.class, Block.class, DefaultContentTypes.getTranslationHandler(RegistryKeys.BLOCK), DefaultLangProcessors.getClassic());
		manager.data(DatagenTests.class, Block.class, DefaultContentTypes.BLOCK_MODELS, BlockStateModelGenerator::registerSimpleCubeAll);
		manager.data(DatagenTests.class, Item.class, DefaultContentTypes.ITEM_MODELS, (generator, item) -> generator.register(item, Models.GENERATED));
	}

	@Override
	public void onInitialize(AdvancedContainer mod) {}

	@Override
	public void onInitializeDataGenerator(AdvancedContainer mod, FabricDataGenerator generator, FabricDataGenerator.Pack pack) {
	}

	private static void register(AdvancedContainer advancedContainer) {
		Registry.register(Registries.BLOCK, new Identifier("mmodding_datagen", "block"), BLOCK);
		Registry.register(Registries.ENTITY_TYPE, new Identifier("mmodding_datagen", "cow"), COW);
	}
}
