package com.mmodding.library.datagen.impl.management.handler;

import com.mmodding.library.datagen.api.management.DataContentType;
import com.mmodding.library.datagen.api.model.item.ItemModelProcessor;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;

import java.util.List;

public class ItemModelTypeImpl implements DataContentType<Item, ItemModelProcessor> {

	@Override
	public void handleContent(FabricDataGenerator.Pack pack, BiList<ItemModelProcessor, List<Item>> contentToProcess) {
		pack.addProvider((output, future) -> new AutomatedModelProvider(contentToProcess, output));
	}

	private static class AutomatedModelProvider extends FabricModelProvider {

		private final BiList<ItemModelProcessor, List<Item>> contentToProcess;

		public AutomatedModelProvider(BiList<ItemModelProcessor, List<Item>> contentToProcess, FabricDataOutput output) {
			super(output);
			this.contentToProcess = contentToProcess;
		}

		@Override
		public void generateItemModels(ItemModelGenerator itemModelGenerator) {
			this.contentToProcess.forEach((processor, items) -> {
				for (Item item : items) {
					processor.process(itemModelGenerator, item);
				}
			});
		}

		@Override
		public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

		}

		@Override
		public String getName() {
			return "Automated " + super.getName();
		}
	}
}
