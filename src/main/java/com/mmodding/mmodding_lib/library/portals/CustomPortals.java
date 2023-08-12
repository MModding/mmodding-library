package com.mmodding.mmodding_lib.library.portals;

import com.mmodding.mmodding_lib.library.portals.squared.CustomSquaredPortalBlock;
import com.mmodding.mmodding_lib.library.portals.squared.UnlinkedCustomSquaredPortal;
import net.minecraft.block.Block;

public abstract class CustomPortals {

	public static UnlinkedCustomSquaredPortal ofSquared(Block frameBlock, CustomSquaredPortalBlock portalBlock, Ignition ignition) {
		return new UnlinkedCustomSquaredPortal(frameBlock, portalBlock, ignition);
	}
}
