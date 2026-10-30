package com.mmodding.library.block.api.catalog.sized;

import com.mmodding.library.block.impl.catalog.sized.SizeManagerImpl;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;

/**
 * A sizing-related {@link Property} manager for {@link SizedBlock}s.
 */
public interface SizeManager {

	static SizeManager create(int xMaxSize, int yMaxSize, int zMaxSize) {
		return new SizeManagerImpl(xMaxSize, yMaxSize, zMaxSize);
	}

	@Nullable IntegerProperty getXProperty();

	@Nullable IntegerProperty getYProperty();

	@Nullable IntegerProperty getZProperty();

	int getLength(BlockState state);

	int getHeight(BlockState state);

	int getWidth(BlockState state);

	Vec3i getInnerPos(BlockState state);

	@ApiStatus.Internal
	BlockState setInnerPos(BlockState state, Vec3i pos);

	@ApiStatus.Internal
	void defineSizeProperties(StateDefinition.Builder<Block, BlockState> builder);
}
