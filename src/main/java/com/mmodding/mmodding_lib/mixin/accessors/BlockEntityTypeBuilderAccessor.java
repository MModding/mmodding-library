package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Set;

@Mixin(BlockEntityType.Builder.class)
public interface BlockEntityTypeBuilderAccessor {

    @Accessor
    <T extends BlockEntity> BlockEntityType.BlockEntityFactory<T> getFactory();

    @Accessor
    Set<Block> getBlocks();
}
