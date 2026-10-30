package com.mmodding.library.block.api.catalog.sized;

import com.mmodding.library.block.impl.catalog.sized.SizeManagerImpl;
import com.mmodding.library.java.api.function.Mapper;
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
@ApiStatus.NonExtendable
public interface SizeManager {

	/**
	 * Creates a {@link SizeManager} for a sized block of a static size.
	 * @param xSize the x-axis size
	 * @param ySize the y-axis size
	 * @param zSize the z-axis size
	 * @return the size manager
	 */
	static SizeManager create(int xSize, int ySize, int zSize) {
		return SizeManager.createDynamicSize(xSize, ySize, zSize, _ -> xSize, _ -> ySize, _ -> zSize);
	}

	/**
	 * Creates a {@link SizeManager} for a sized block with a dynamic size.
	 * @param xMaxSize the max possible size on the x-axis
	 * @param yMaxSize the max possible size on the y-axis
	 * @param zMaxSize the max possible size on the z-axis
	 * @param xSizeMapper the size mapper of the x-axis
	 * @param ySizeMapper the size mapper of the y-axis
	 * @param zSizeMapper the size mapper of the z-axis
	 * @return the size manager
	 */
	static SizeManager createDynamicSize(
		int xMaxSize,
		int yMaxSize,
		int zMaxSize,
		Mapper<BlockState, Integer> xSizeMapper,
		Mapper<BlockState, Integer> ySizeMapper,
		Mapper<BlockState, Integer> zSizeMapper
	) {
		return new SizeManagerImpl(xMaxSize, yMaxSize, zMaxSize, xSizeMapper, ySizeMapper, zSizeMapper);
	}

	/**
	 * The inner x-axis representative property.
	 * @return the property
	 * @exception NullPointerException if the provided xMaxSize at object creation is 1
	 */
	@Nullable IntegerProperty getXProperty();

	/**
	 * The inner y-axis representative property.
	 * @return the property
	 * @exception NullPointerException if the provided yMaxSize at object creation is 1
	 */
	@Nullable IntegerProperty getYProperty();

	/**
	 * The inner z-axis representative property.
	 * @return the property
	 * @exception NullPointerException if the provided zMaxSize at object creation is 1
	 */
	@Nullable IntegerProperty getZProperty();

	/**
	 * Maps the inner length (west->east, x-axis) of the sized block from a block state.
	 * @param state the block state
	 * @return the inner length
	 */
	int getLength(BlockState state);

	/**
	 * Maps the inner height (bottom->top, y-axis) of the sized block from a block state.
	 * @param state the block state
	 * @return the inner height
	 */
	int getHeight(BlockState state);

	/**
	 * Maps the inner width (north->south, z-axis) of the sized block from a block state.
	 * @param state the block state
	 * @return the inner width
	 */
	int getWidth(BlockState state);

	/**
	 * Returns the inner position of the sized block for the given block state.
	 * @param state the block state
	 * @return the inner position
	 */
	Vec3i getInnerPos(BlockState state);

	@ApiStatus.Internal
	BlockState setInnerPos(BlockState state, Vec3i pos);

	@ApiStatus.Internal
	void defineSizeProperties(StateDefinition.Builder<Block, BlockState> builder);
}
