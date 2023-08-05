package com.mmodding.mmodding_lib.library.blockentities;

import com.google.common.collect.ImmutableSet;
import com.mmodding.mmodding_lib.mixin.accessors.BlockEntityTypeBuilderAccessor;
import com.mojang.datafixers.types.Type;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomBlockEntityType<T extends BlockEntity> extends BlockEntityType<T> implements BlockEntityTypeRegistrable<T> {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	public CustomBlockEntityType(BlockEntityFactory<? extends T> factory, Set<Block> blocks, Type<?> type) {
		super(factory, blocks, type);
	}

	public static <T extends BlockEntity> CustomBlockEntityType<T> create(BlockEntityType.BlockEntityFactory<T> factory, Type<?> dataFixerType, Block... blocks) {
		return Builder.create(factory, blocks).buildCustom(dataFixerType);
	}

	@Override
	public boolean isNotRegistered() {
		return !this.registered.get();
	}

	@Override
	public void setRegistered() {
		this.registered.set(true);
	}

	public static class Builder<T extends BlockEntity> extends BlockEntityType.Builder<T> {

		private Builder(BlockEntityType.BlockEntityFactory<? extends T> factory, Set<Block> blocks) {
			super(factory, blocks);
		}

		public static <T extends BlockEntity> Builder<T> create(BlockEntityType.BlockEntityFactory<? extends T> factory, Block... blocks) {
			return new Builder<>(factory, ImmutableSet.copyOf(blocks));
		}

		public CustomBlockEntityType<T> buildCustom(Type<?> type) {
			BlockEntityTypeBuilderAccessor accessor = (BlockEntityTypeBuilderAccessor) this;
			return new CustomBlockEntityType<>(accessor.getFactory(), accessor.getBlocks(), type);
		}
	}
}
