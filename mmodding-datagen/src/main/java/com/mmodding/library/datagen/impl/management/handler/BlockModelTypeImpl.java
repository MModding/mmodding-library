package com.mmodding.library.datagen.impl.management.handler;

import com.mmodding.library.datagen.api.management.DataContentType;
import com.mmodding.library.datagen.api.model.block.BlockStateModelProcessor;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

import java.util.List;

public class BlockModelTypeImpl implements DataContentType<Block, BlockStateModelProcessor> {

	@Override
	public void handleContent(FabricDataGenerator.Pack pack, BiList<BlockStateModelProcessor, List<Block>> contentToProcess) {
		pack.addProvider((output, future) -> new AutomatedBlockStateProvider(contentToProcess, output));
	}

	private static class AutomatedBlockStateProvider extends FabricModelProvider {

		private final BiList<BlockStateModelProcessor, List<Block>> contentToProcess;

		public AutomatedBlockStateProvider(BiList<BlockStateModelProcessor, List<Block>> contentToProcess, FabricDataOutput output) {
			super(output);
			this.contentToProcess = contentToProcess;
		}

		@Override
		public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
			this.contentToProcess.forEach((processor, blocks) -> {
				for (Block block : blocks) {
					processor.process(blockStateModelGenerator, block);
				}
			});
		}

		@Override
		public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		}

		@Override
		public String getName() {
			return "Automated " + super.getName();
		}
	}
}
