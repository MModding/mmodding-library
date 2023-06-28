package com.mmodding.mmodding_lib.library.pois;

import com.mmodding.mmodding_lib.mixin.accessors.PointOfInterestTypesAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomPOI implements POIRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

	private final PointOfInterestType type;

	public static Set<BlockState> getStates(Block block) {
		return PointOfInterestTypesAccessor.states(block);
	}

    public CustomPOI(Block block, int maxTickets, int searchDistance) {
		this(getStates(block), maxTickets, searchDistance);
	}

	public CustomPOI(Set<BlockState> states, int maxTickets, int searchDistance) {
		this.type = new PointOfInterestType(states, maxTickets, searchDistance);
	}

	public PointOfInterestType getType() {
		return this.type;
	}

    @Override
    public boolean isNotRegistered() {
        return !registered.get();
    }

    @Override
    public void setRegistered() {
        registered.set(true);
    }
}
