package com.mmodding.library.datagen.test;

import com.mmodding.library.core.api.container.AdvancedContainer;
import com.mmodding.library.core.api.management.ElementsManager;
import com.mmodding.library.core.api.management.initializer.ExtendedModInitializer;
import com.mmodding.library.datagen.api.lang.LangProcessor;
import com.mmodding.library.datagen.api.loot.block.BlockLootProcessor;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class DatagenTests implements ExtendedModInitializer {

	public static final Block BLOCK = new Block(FabricBlockSettings.create().mapColor(MapColor.BLACK))
		.loot(BlockLootProcessor.standard())
		.lang(LangProcessor.standard());

	public static final EntityType<CowEntity> COW = FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CowEntity::new).build()
		.loot(entityType -> LootTable.builder())
		.lang(LangProcessor.standard());

	@Override
	public void setupManager(ElementsManager.Builder manager) {
		manager.content(DatagenTests::register);
	}

	@Override
	public void onInitialize(AdvancedContainer mod) {}

	private static void register(AdvancedContainer advancedContainer) {
		Registry.register(Registries.BLOCK, new Identifier("mmodding_datagen", "block"), BLOCK);
		Registry.register(Registries.ENTITY_TYPE, new Identifier("mmodding_datagen", "cow"), COW);
	}
}
