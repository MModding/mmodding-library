package com.mmodding.library.block.entity.api;

import com.mmodding.library.block.entity.impl.duck.BlockEntityTypeDuck;
import dev.yumi.commons.event.Event;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;

import java.util.List;

@FunctionalInterface
public interface BlockEntityTypeSupport {

	void mutateSupportedBlocks(List<Block> supportedBlocks);

	static <T extends BlockEntity> Event<Identifier, BlockEntityTypeSupport> callbackOf(BlockEntityType<T> blockEntityType) {
		return BlockEntityTypeDuck.get(blockEntityType).mmodding$supportCallback();
	}
}
