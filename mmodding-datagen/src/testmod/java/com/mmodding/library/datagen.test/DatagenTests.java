package com.mmodding.library.datagen.test;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.core.api.ExtendedModInitializer;
import com.mmodding.library.datagen.api.ExtendedDataGeneratorEntrypoint;
import com.mmodding.library.datagen.api.lang.DefaultLangProcessors;
import com.mmodding.library.datagen.api.loot.block.DefaultBlockLootProcessors;
import com.mmodding.library.datagen.api.management.DataManager;
import com.mmodding.library.datagen.api.management.DefaultDataHandlers;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

public class DatagenTests implements ExtendedModInitializer, ExtendedDataGeneratorEntrypoint {

	public static final Block BLOCK = new Block(Block.Properties.of().mapColor(MapColor.COLOR_BLACK));

	public static final EntityType<Cow> COW = EntityType.Builder.of(Cow::new, MobCategory.CREATURE).build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("mmodding_datagen", "cow")));

	@Override
	public void setupManager(ElementsManager manager) {
		manager.content(DatagenTests::register);
	}

	@Override
	public void setupManager(DataManager manager) {
		manager.task(DatagenTests.class, DefaultDataHandlers.getTranslationHandler(Registries.BLOCK, Block.class), DefaultLangProcessors.getClassic());
		manager.task(DatagenTests.class, DefaultDataHandlers.BLOCK_MODELS, BlockModelGenerators::createTrivialCube);
		manager.task(DatagenTests.class, DefaultDataHandlers.BLOCK_LOOTS, DefaultBlockLootProcessors.SIMPLE);
		manager.task(DatagenTests.class, DefaultDataHandlers.ITEM_MODELS, (generator, item) -> generator.generateFlatItem(item, ModelTemplates.FLAT_ITEM));
	}

	@Override
	public void onInitialize(AdvancedContainer mod) {}

	@Override
	public void onInitializeDataGenerator(AdvancedContainer mod, FabricDataGenerator generator, FabricDataGenerator.Pack pack) {
	}

	private static void register(AdvancedContainer advancedContainer) {
		Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath("mmodding_datagen", "block"), BLOCK);
		Registry.register(BuiltInRegistries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("mmodding_datagen", "cow"), COW);
	}
}
