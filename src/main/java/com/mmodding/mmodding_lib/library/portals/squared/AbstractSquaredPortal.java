package com.mmodding.mmodding_lib.library.portals.squared;

import com.mmodding.mmodding_lib.library.helpers.CustomSquaredPortalAreaHelper;
import com.mmodding.mmodding_lib.library.portals.*;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

import java.util.Optional;

public abstract class AbstractSquaredPortal extends AbstractPortal {

	protected AbstractSquaredPortal(Block frameBlock, CustomPortalBlock portalBlock, Ignition ignition) {
		super(frameBlock, portalBlock, ignition);
	}

	@Override
	public CustomSquaredPortalBlock getPortalBlock() {
		return (CustomSquaredPortalBlock) super.getPortalBlock().getBlock();
	}

	public Optional<CustomSquaredPortalAreaHelper> getNewCustomPortal(WorldAccess world, BlockPos pos, Direction.Axis axis) {
		return CustomSquaredPortalAreaHelper.getNewCustomPortal(this.getFrameBlock(), this.getPortalBlock(), world, pos, axis);
	}

	public boolean wasAlreadyValid(WorldAccess world, BlockPos pos, Direction.Axis axis) {
		return new CustomSquaredPortalAreaHelper(this.getFrameBlock(), this.getPortalBlock(), world, pos, axis).wasAlreadyValid();
	}
}
