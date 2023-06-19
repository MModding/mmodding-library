package com.mmodding.mmodding_lib.library.worldgen.features.trees;

import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class CustomTrunkPlacer extends TrunkPlacer implements TreeElementRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

    public CustomTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

	@Override
	public abstract TrunkPlacerType<?> getType();

	@Override
    public boolean isNotRegistered() {
        return !registered.get();
    }

    @Override
    public void setRegistered() {
        registered.set(true);
    }
}
