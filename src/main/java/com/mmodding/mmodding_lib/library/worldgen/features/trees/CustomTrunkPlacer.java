package com.mmodding.mmodding_lib.library.worldgen.features.trees;

import com.mmodding.mmodding_lib.mixin.accessors.TrunkPlacerTypeAccessor;
import com.mojang.serialization.Codec;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class CustomTrunkPlacer extends TrunkPlacer implements TreeElementRegistrable {

	public static <P extends TrunkPlacer> TrunkPlacerType<P> createType(Codec<P> codec) {
		return TrunkPlacerTypeAccessor.create(codec);
	}

    private final AtomicBoolean registered = new AtomicBoolean(false);

    public CustomTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

	@Override
	public abstract TrunkPlacerType<?> getType();

	@Override
    public boolean isNotRegistered() {
        return !this.registered.get();
    }

    @Override
    public void setRegistered() {
		this.registered.set(true);
    }
}
