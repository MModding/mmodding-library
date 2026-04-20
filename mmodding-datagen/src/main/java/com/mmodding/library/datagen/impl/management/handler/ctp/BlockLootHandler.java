package com.mmodding.library.datagen.impl.management.handler.ctp;

import com.mmodding.library.datagen.api.loot.block.BlockLootProcessor;
import com.mmodding.library.datagen.api.management.handler.DataProcessHandler;
import com.mmodding.library.java.api.list.BiList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@ApiStatus.Internal
public class BlockLootHandler implements DataProcessHandler<Block, BlockLootProcessor> {

	@Override
	public Class<Block> getType() {
		return Block.class;
	}

	@Override
	public void handleContent(FabricDataGenerator.Pack pack, BiList<BlockLootProcessor, List<Block>> contentToProcess) {
		DataProcessHandler.provider(pack, contentToProcess, AutomatedBlockLootProvider::new);
	}

	private static class AutomatedBlockLootProvider extends FabricBlockLootSubProvider {

		private final BiList<BlockLootProcessor, List<Block>> contentToProcess;

		public AutomatedBlockLootProvider(BiList<BlockLootProcessor, List<Block>> contentToProcess, FabricPackOutput output, CompletableFuture<HolderLookup.Provider> future) {
			super(output, future);
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
