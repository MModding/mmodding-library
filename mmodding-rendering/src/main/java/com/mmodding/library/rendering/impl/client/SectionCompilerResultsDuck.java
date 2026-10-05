package com.mmodding.library.rendering.impl.client;

import com.mmodding.library.core.api.management.info.DuckInterface;
import com.mmodding.library.java.api.list.BiList;
import net.minecraft.client.renderer.chunk.SectionCompiler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

@DuckInterface(SectionCompiler.Results.class)
public interface SectionCompilerResultsDuck {

	BiList<BlockPos, BlockState> mmodding$specialBlocks();
}
