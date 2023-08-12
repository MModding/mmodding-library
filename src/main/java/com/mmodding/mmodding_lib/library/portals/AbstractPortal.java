package com.mmodding.mmodding_lib.library.portals;

import net.minecraft.block.Block;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractPortal implements PortalRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	protected final Block frameBlock;
	protected final CustomPortalBlock portalBlock;
	protected final Ignition ignition;

	protected AbstractPortal(Block frameBlock, CustomPortalBlock portalBlock, Ignition ignition) {
		this.frameBlock = frameBlock;
		this.portalBlock = portalBlock;
		this.ignition = ignition;
	}

	protected void register(CustomPortalLink link) {
		link.register(this.portalBlock);
	}

	public Block getFrameBlock() {
		return this.frameBlock;
	}

	public CustomPortalBlock getPortalBlock() {
		return this.portalBlock;
	}

	public Ignition getIgnition() {
		return this.ignition;
	}

	@Override
	public boolean isNotRegistered() {
		return !this.registered.get();
	}

	@Override
	public void setRegistered() {
		this.registered.set(true);
	}
}
