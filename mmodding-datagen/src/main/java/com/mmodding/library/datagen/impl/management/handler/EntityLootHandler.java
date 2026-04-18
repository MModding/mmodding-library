package com.mmodding.library.datagen.impl.management.handler;

import com.mmodding.library.datagen.api.loot.entity.EntityLootProcessor;
import com.mmodding.library.datagen.api.management.handler.DataProcessHandler;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@ApiStatus.Internal
public class EntityLootHandler implements DataProcessHandler<EntityType<?>, EntityLootProcessor> {

	@Override
	@SuppressWarnings("unchecked")
	public Class<EntityType<?>> getType() {
		return (Class<EntityType<?>>) (Object) EntityType.class;
	}

	@Override
	public void handleContent(FabricDataGenerator.Pack pack, BiList<EntityLootProcessor, List<EntityType<?>>> contentToProcess) {
		pack.addProvider((output, future) -> new AutomatedEntityLootProvider(contentToProcess, future, output));
	}

	private static class AutomatedEntityLootProvider extends SimpleFabricLootTableSubProvider {

		private final BiList<EntityLootProcessor, List<EntityType<?>>> contentToProcess;

		public AutomatedEntityLootProvider(BiList<EntityLootProcessor, List<EntityType<?>>> contentToProcess, CompletableFuture<HolderLookup.Provider> future, FabricPackOutput output) {
			super(output, future, LootContextParamSets.ENTITY);
			this.contentToProcess = contentToProcess;
		}

		@Override
		public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {
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
