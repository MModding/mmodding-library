package com.mmodding.mmodding_lib.library.portals.squared;

import com.mmodding.mmodding_lib.library.portals.CustomPortalBlock;
import com.mmodding.mmodding_lib.library.portals.CustomPortalLink;
import com.mmodding.mmodding_lib.library.portals.Ignition;
import net.minecraft.block.Block;

public class UnlinkedCustomSquaredPortal extends AbstractSquaredPortal {

	public UnlinkedCustomSquaredPortal(Block frameBlock, CustomPortalBlock portalBlock, Ignition ignition) {
		super(frameBlock, portalBlock, ignition);
	}

	public CustomSquaredPortal applyLink(CustomPortalLink link) {
		this.register(link);
		return new CustomSquaredPortal(this.frameBlock, (CustomSquaredPortalBlock) this.portalBlock, this.ignition, link);
	}
}
