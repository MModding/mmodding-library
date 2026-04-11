package com.mmodding.library.datagen.impl.management.handler;

import com.mmodding.library.datagen.api.loot.block.BlockLootProcessor;
import com.mmodding.library.datagen.api.management.DataContentType;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.world.level.block.Block;
import java.util.List;

public class BlockLootTypeImpl implements DataContentType<Block, BlockLootProcessor> {

	@Override
	public void handleContent(FabricDataGenerator.Pack pack, BiList<BlockLootProcessor, List<Block>> contentToProcess) {
		pack.addProvider((output, future) -> new AutomatedBlockLootProvider(contentToProcess, output));
	}

	private static class AutomatedBlockLootProvider extends FabricBlockLootTableProvider {

		private final BiList<BlockLootProcessor, List<Block>> contentToProcess;

		public AutomatedBlockLootProvider(BiList<BlockLootProcessor, List<Block>> contentToProcess, FabricDataOutput output) {
			super(output);
			this.contentToProcess = contentToProcess;
		}

		@Override
		public void generate() {
			this.contentToProcess.forEach((processor, blocks) -> {
				for (Block block : blocks) {
					processor.process(this, block);
				}
			});
		}

		@Override
		public String getName() {
			return "Automated " + super.getName();
		}
	}
}
