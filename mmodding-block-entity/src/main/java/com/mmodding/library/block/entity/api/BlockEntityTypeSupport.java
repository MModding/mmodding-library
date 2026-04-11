package com.mmodding.library.block.entity.api;

import com.mmodding.library.block.entity.impl.duck.BlockEntityTypeDuck;
import dev.yumi.commons.event.Event;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

@FunctionalInterface
public interface BlockEntityTypeSupport {

	void mutateSupportedBlocks(List<Block> supportedBlocks);

	static <T extends BlockEntity> Event<ResourceLocation, BlockEntityTypeSupport> callbackOf(BlockEntityType<T> blockEntityType) {
		return BlockEntityTypeDuck.get(blockEntityType).mmodding$supportCallback();
	}
}
