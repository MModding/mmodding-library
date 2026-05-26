package com.mmodding.library.block.api.catalog;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.BedBlock;

/**
 * A {@link BedBlock} that inverts the horizontal facing direction (in the opposite of vanilla beds),
 * to match the usual standard of horizontal facing blocks to apply horizontal models in the same way as usual.
 * @implNote <br>relies on {@link com.mmodding.library.block.mixin.BedBlockMixin} for direction inverts
 */
public class SimpleBedBlock extends BedBlock {

	public SimpleBedBlock(Properties settings) {
		super(DyeColor.WHITE, settings);
	}
}
