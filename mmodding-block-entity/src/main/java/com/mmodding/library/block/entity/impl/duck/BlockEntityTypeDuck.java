package com.mmodding.library.block.entity.impl.duck;

import com.mmodding.library.block.entity.api.BlockEntityTypeSupport;
import dev.yumi.commons.event.Event;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface BlockEntityTypeDuck {

	Event<ResourceLocation, BlockEntityTypeSupport> mmodding$supportCallback();

	static <T extends BlockEntity> BlockEntityTypeDuck get(BlockEntityType<T> blockEntityType) {
		if (!(blockEntityType instanceof BlockEntityTypeDuck)) {
			throw new IllegalArgumentException("Unsupported BlockEntityType: " + blockEntityType);
		}
		return (BlockEntityTypeDuck) blockEntityType;
	}
}
