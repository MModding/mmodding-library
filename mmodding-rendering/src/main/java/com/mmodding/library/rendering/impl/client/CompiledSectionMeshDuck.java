package com.mmodding.library.rendering.impl.client;

import com.mmodding.library.core.api.management.info.DuckInterface;
import com.mmodding.library.java.api.list.BiList;
import net.minecraft.client.renderer.chunk.CompiledSectionMesh;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

@DuckInterface(CompiledSectionMesh.class)
public interface CompiledSectionMeshDuck {

	BiList<BlockPos, BlockState> mmodding$specialBlocks();
}
