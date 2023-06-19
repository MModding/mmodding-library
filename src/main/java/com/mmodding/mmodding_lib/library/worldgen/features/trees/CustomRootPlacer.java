package com.mmodding.mmodding_lib.library.worldgen.features.trees;

import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.root.AboveRootPlacement;
import net.minecraft.world.gen.root.RootPlacer;
import net.minecraft.world.gen.root.RootPlacerType;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class CustomRootPlacer extends RootPlacer implements TreeElementRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

    public CustomRootPlacer(IntProvider trunkOffsetY, BlockStateProvider rootProvider, Optional<AboveRootPlacement> aboveRootPlacement) {
		super(trunkOffsetY, rootProvider, aboveRootPlacement);
	}

	@Override
	public abstract RootPlacerType<?> getType();

	@Override
    public boolean isNotRegistered() {
        return !registered.get();
    }

    @Override
    public void setRegistered() {
        registered.set(true);
    }
}
