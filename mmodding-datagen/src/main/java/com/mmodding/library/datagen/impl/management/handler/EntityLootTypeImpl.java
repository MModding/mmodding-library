package com.mmodding.library.datagen.impl.management.handler;

import com.mmodding.library.datagen.api.loot.entity.EntityLootProcessor;
import com.mmodding.library.datagen.api.management.DataContentType;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import java.util.List;
import java.util.function.BiConsumer;

public class EntityLootTypeImpl implements DataContentType<EntityType<?>, EntityLootProcessor> {

	@Override
	public void handleContent(FabricDataGenerator.Pack pack, BiList<EntityLootProcessor, List<EntityType<?>>> contentToProcess) {
		pack.addProvider((output, future) -> new AutomatedEntityLootProvider(contentToProcess, output));
	}

	private static class AutomatedEntityLootProvider extends SimpleFabricLootTableProvider {

		private final BiList<EntityLootProcessor, List<EntityType<?>>> contentToProcess;

		public AutomatedEntityLootProvider(BiList<EntityLootProcessor, List<EntityType<?>>> contentToProcess, FabricDataOutput output) {
			super(output, LootContextParamSets.ENTITY);
			this.contentToProcess = contentToProcess;
		}

		@Override
		public void generate(BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
			this.contentToProcess.forEach((processor, entityTypes) -> {
				for (EntityType<?> entityType : entityTypes) {
					processor.process(entityType);
				}
			});
		}

		@Override
		public String getName() {
			return "Automated " + super.getName();
		}
	}
}
