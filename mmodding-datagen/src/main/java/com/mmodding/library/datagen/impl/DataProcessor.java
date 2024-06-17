package com.mmodding.library.datagen.impl;

import com.mmodding.library.datagen.api.lang.LangContainer;
import com.mmodding.library.datagen.impl.access.LangProcessorAccess;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.stat.StatType;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.Internal
public class DataProcessor {

	public static DataGeneratorEntrypoint process(DataContainers containers) {
		return fabricDataGenerator -> {
			FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
			pack.addProvider((output, future) -> new AutomatedLanguageProvider(containers.langContainers(), output));
		};
	}

	private static class AutomatedLanguageProvider extends FabricLanguageProvider {

		private final List<LangContainer> langContainers;

		protected AutomatedLanguageProvider(List<LangContainer> langContainers, FabricDataOutput dataOutput) {
			super(dataOutput);
			this.langContainers = langContainers;
		}

		@Override
		@SuppressWarnings("unchecked")
		public void generateTranslations(TranslationBuilder translationBuilder) {
			for (LangContainer container : this.langContainers) {
				switch (container.type()) {
					case ITEM -> Registries.ITEM.getKey((Item) container)
						.ifPresentOrElse(
							key -> translationBuilder.add((Item) container, ((LangProcessorAccess<Item>) container).processor().process(key)),
							() -> {throw new IllegalStateException("Item does not exist in Registries.ITEM!");}
						);
					case BLOCK -> Registries.BLOCK.getKey((Block) container)
						.ifPresentOrElse(
							key -> translationBuilder.add((Block) container, ((LangProcessorAccess<Block>) container).processor().process(key)),
							() -> {throw new IllegalStateException("Block does not exist in Registries.BLOCK!");}
						);
					case ENTITY -> Registries.ENTITY_TYPE.getKey((EntityType<?>) container)
						.ifPresentOrElse(
							key -> translationBuilder.add((EntityType<?>) container, ((LangProcessorAccess<EntityType<?>>) container).processor().process(key)),
							() -> {throw new IllegalStateException("EntityType does not exist in Registries.ENTITY_TYPE!");}
						);
					case ATTRIBUTE -> Registries.ENTITY_ATTRIBUTE.getKey((EntityAttribute) container)
						.ifPresentOrElse(
							key -> translationBuilder.add((EntityAttribute) container, ((LangProcessorAccess<EntityAttribute>) container).processor().process(key)),
							() -> {throw new IllegalStateException("EntityAttribute does not exist in Registries.ENTITY_ATTRIBUTE!");}
						);
					case STATISTIC -> Registries.STAT_TYPE.getKey((StatType<?>) container)
						.ifPresentOrElse(
							key -> translationBuilder.add((StatType<?>) container, ((LangProcessorAccess<StatType<?>>) container).processor().process(key)),
							() -> {throw new IllegalStateException("StatType does not exist in Registries.STAT_TYPE!");}
						);
				}
			}
		}
	}
}
