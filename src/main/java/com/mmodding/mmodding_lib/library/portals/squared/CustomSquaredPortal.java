package com.mmodding.mmodding_lib.library.portals.squared;

import com.mmodding.mmodding_lib.library.portals.CustomPortalLink;
import com.mmodding.mmodding_lib.library.portals.Ignition;
import net.minecraft.block.Block;

public class CustomSquaredPortal extends AbstractSquaredPortal {

	protected final CustomPortalLink portalLink;

	CustomSquaredPortal(Block frameBlock, CustomSquaredPortalBlock portalBlock, Ignition ignition, CustomPortalLink portalLink) {
		super(frameBlock, portalBlock, ignition);
		this.portalLink = portalLink;
	}

	public CustomPortalLink getPortalLink() {
		return this.portalLink;
	}
}
