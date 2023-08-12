package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.portals.CustomPortals;
import com.mmodding.mmodding_lib.library.portals.squared.CustomSquaredPortal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.Optional;

public interface PortalForcerDuckInterface {

	void mmodding_lib$setUseCustomPortalElements(boolean useCustomPortalElements);

	Optional<BlockLocating.Rectangle> mmodding_lib$searchCustomPortal(RegistryKey<PointOfInterestType> poiKey, BlockPos destPos, WorldBorder worldBorder);

	void mmodding_lib$setCustomPortal(CustomSquaredPortal customPortal);
}
