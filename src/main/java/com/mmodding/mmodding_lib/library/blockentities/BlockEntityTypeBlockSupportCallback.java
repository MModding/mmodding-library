package com.mmodding.mmodding_lib.library.blockentities;

import com.mmodding.mmodding_lib.ducks.BlockEntityTypeDuckInterface;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import org.quiltmc.qsl.base.api.event.Event;

import java.util.List;

public interface BlockEntityTypeBlockSupportCallback {

	void onSupportedBlocks(List<Block> supportedBlocks);

	static <T extends BlockEntity> Event<BlockEntityTypeBlockSupportCallback> blockEntityType(BlockEntityType<T> blockEntityType) {
		return BlockEntityTypeDuckInterface.get(blockEntityType).mmodding_lib$supportBlocksCallback();
	}
}
