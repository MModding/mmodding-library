package com.mmodding.library.datagen.test;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.core.api.ExtendedModInitializer;
import com.mmodding.library.datagen.api.ExtendedDataGeneratorEntrypoint;
import com.mmodding.library.datagen.api.lang.DefaultLangProcessors;
import com.mmodding.library.datagen.api.loot.block.DefaultBlockLootProcessors;
import com.mmodding.library.datagen.api.management.DataManager;
import com.mmodding.library.datagen.api.management.DefaultContentTypes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

public class DatagenTests implements ExtendedModInitializer, ExtendedDataGeneratorEntrypoint {

	public static final Block BLOCK = new Block(FabricBlockSettings.create().mapColor(MapColor.COLOR_BLACK));

	public static final EntityType<Cow> COW = FabricEntityTypeBuilder.create(MobCategory.CREATURE, Cow::new).build();

	@Override
	public void setupManager(ElementsManager manager) {
		manager.content(DatagenTests::register);
	}

	@Override
	public void setupManager(DataManager manager) {
		manager.task(DatagenTests.class, Block.class, DefaultContentTypes.getTranslationHandler(Registries.BLOCK), DefaultLangProcessors.getClassic());
		manager.task(DatagenTests.class, Block.class, DefaultContentTypes.BLOCK_MODELS, BlockModelGenerators::createGenericCube);
		manager.task(DatagenTests.class, Block.class, DefaultContentTypes.BLOCK_LOOTS, DefaultBlockLootProcessors.SIMPLE);
		manager.task(DatagenTests.class, Item.class, DefaultContentTypes.ITEM_MODELS, (generator, item) -> generator.generateFlatItem(item, ModelTemplates.FLAT_ITEM));
	}

	@Override
	public void onInitialize(AdvancedContainer mod) {}

	@Override
	public void onInitializeDataGenerator(AdvancedContainer mod, FabricDataGenerator generator, FabricDataGenerator.Pack pack) {
	}

	private static void register(AdvancedContainer advancedContainer) {
		Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation("mmodding_datagen", "block"), BLOCK);
		Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation("mmodding_datagen", "cow"), COW);
	}
}
