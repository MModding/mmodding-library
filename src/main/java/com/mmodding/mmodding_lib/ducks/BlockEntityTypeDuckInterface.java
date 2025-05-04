package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.blockentities.BlockEntityTypeBlockSupportCallback;
import com.mmodding.mmodding_lib.library.utils.InternalOf;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;

@InternalOf(BlockEntityTypeBlockSupportCallback.class)
public interface BlockEntityTypeDuckInterface {

	Event<BlockEntityTypeBlockSupportCallback> mmodding_lib$supportBlocksCallback();

	static <T extends BlockEntity> BlockEntityTypeDuckInterface get(BlockEntityType<T> blockEntityType) {
		if (!(blockEntityType instanceof BlockEntityTypeDuckInterface)) {
			throw new IllegalArgumentException("Unsupported BlockEntityType: " + blockEntityType);
		}
		return (BlockEntityTypeDuckInterface) blockEntityType;
	}
}
