package com.mmodding.library.block.api.catalog;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public abstract class LootingPropertiesEntityBlock extends BaseEntityBlock {

	protected LootingPropertiesEntityBlock(Properties properties) {
		super(properties);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity != null) {
			ItemStack stack = new ItemStack(this);
			stack.applyComponents(blockEntity.collectComponents());
			BlockItemStateProperties itemStateProperties = BlockItemStateProperties.EMPTY;
			for (Property<? extends Comparable<?>> property : state.getProperties()) {
				itemStateProperties = itemStateProperties.with(property, (Comparable) state.getValue(property));
			}
			ItemEntity entity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), stack);
			entity.setDefaultPickUpDelay();
			level.addFreshEntity(entity);
		}
		return super.playerWillDestroy(level, pos, state, player);
	}
}
