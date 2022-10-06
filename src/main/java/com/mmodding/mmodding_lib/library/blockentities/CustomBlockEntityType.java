package com.mmodding.mmodding_lib.library.blockentities;

import com.mmodding.mmodding_lib.mixin.accessors.BlockEntityTypeAccessor;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomBlockEntityType<T extends BlockEntity> {

	private final FabricBlockEntityTypeBuilder.Factory<? extends T> factory;
	private final List<Block> blockList;
	private BlockEntityType<T> blockEntityType = null;

	public CustomBlockEntityType(FabricBlockEntityTypeBuilder.Factory<? extends T> factory, Block... blocks) {
		this.factory = factory;
		this.blockList = new ArrayList<>(blocks.length);
		Collections.addAll(this.blockList, blocks);
	}

	public CustomBlockEntityType<T> createAndRegister(Identifier identifier) {
		this.blockEntityType = BlockEntityTypeAccessor.create(identifier.toString(), BlockEntityType.Builder.create(factory::create, blockList.toArray(new Block[0])));
		return this;
	}

	@Nullable
	public BlockEntityType<T> getBlockEntityTypeIfCreated() {
		return blockEntityType;
	}
}
