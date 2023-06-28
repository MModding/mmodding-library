package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.blocks.CustomSquaredPortalBlock;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.Optional;

public interface PortalForcerDuckInterface {

	void setUseCustomPortalElements(boolean useCustomPortalElements);

	Optional<BlockLocating.Rectangle> searchCustomPortal(RegistryKey<PointOfInterestType> poiKey, BlockPos destPos, WorldBorder worldBorder);

	void setCustomPortalElements(Block frameBlock, CustomSquaredPortalBlock portalBlock);
}
