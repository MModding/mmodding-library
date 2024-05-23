package com.mmodding.library.block.entity.impl.duck;

import com.mmodding.library.block.entity.api.BlockEntityTypeSupport;
import dev.yumi.commons.event.Event;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;

public interface BlockEntityTypeDuck {

	Event<Identifier, BlockEntityTypeSupport> mmodding$supportCallback();

	static <T extends BlockEntity> BlockEntityTypeDuck get(BlockEntityType<T> blockEntityType) {
		if (!(blockEntityType instanceof BlockEntityTypeDuck)) {
			throw new IllegalArgumentException("Unsupported BlockEntityType: " + blockEntityType);
		}
		return (BlockEntityTypeDuck) blockEntityType;
	}
}
