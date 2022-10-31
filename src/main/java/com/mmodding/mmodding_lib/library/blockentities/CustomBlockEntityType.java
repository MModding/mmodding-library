package com.mmodding.mmodding_lib.library.blockentities;

import com.mmodding.mmodding_lib.mixin.accessors.BlockEntityTypeAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomBlockEntityType<T extends BlockEntity> {

	private final BlockEntityType.BlockEntityFactory<? extends T> factory;
	private final List<Block> blockList;
	private BlockEntityType<T> blockEntityType = null;

	public CustomBlockEntityType(BlockEntityType.BlockEntityFactory<? extends T> factory, Block... blocks) {
		this.factory = factory;
		this.blockList = new ArrayList<>(blocks.length);
		Collections.addAll(this.blockList, blocks);
	}

	public CustomBlockEntityType<T> createAndRegister(Identifier identifier) {
		this.blockEntityType = BlockEntityTypeAccessor.create(identifier.toString(), BlockEntityType.Builder.create(factory, blockList.toArray(new Block[0])));
		return this;
	}

	@Nullable
	public BlockEntityType<T> getBlockEntityTypeIfCreated() {
		return blockEntityType;
	}
}
