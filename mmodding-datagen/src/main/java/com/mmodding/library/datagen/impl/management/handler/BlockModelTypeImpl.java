package com.mmodding.library.datagen.impl.management.handler;

import com.mmodding.library.datagen.api.management.DataContentType;
import com.mmodding.library.datagen.api.model.block.BlockStateModelProcessor;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.world.level.block.Block;
import java.util.List;

public class BlockModelTypeImpl implements DataContentType<Block, BlockStateModelProcessor> {

	@Override
	public void handleContent(FabricDataGenerator.Pack pack, BiList<BlockStateModelProcessor, List<Block>> contentToProcess) {
		pack.addProvider((output, future) -> new AutomatedBlockStateProvider(contentToProcess, output));
	}

	private static class AutomatedBlockStateProvider extends FabricModelProvider {

		private final BiList<BlockStateModelProcessor, List<Block>> contentToProcess;

		public AutomatedBlockStateProvider(BiList<BlockStateModelProcessor, List<Block>> contentToProcess, FabricPackOutput output) {
			super(output);
			this.contentToProcess = contentToProcess;
		}

		@Override
		public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
			this.contentToProcess.forEach((processor, blocks) -> {
				for (Block block : blocks) {
					processor.process(blockStateModelGenerator, block);
				}
			});
		}

		@Override
		public void generateItemModels(ItemModelGenerators itemModelGenerator) {
		}

		@Override
		public String getName() {
			return "Automated Block " + super.getName();
		}
	}
}
