package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockEntityType.class)
public interface BlockEntityTypeAccessor {
	@Invoker("create")
	static <T extends BlockEntity> BlockEntityType<T> create(String id, BlockEntityType.Builder<T> builder) {
		throw new AssertionError();
	}
}
