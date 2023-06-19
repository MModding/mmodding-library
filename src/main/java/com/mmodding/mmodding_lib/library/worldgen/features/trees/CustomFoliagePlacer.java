package com.mmodding.mmodding_lib.library.worldgen.features.trees;

import com.mmodding.mmodding_lib.mixin.accessors.FoliagePlacerTypeAccessor;
import com.mojang.datafixers.Products.P3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder.*;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class CustomFoliagePlacer extends FoliagePlacer implements TreeElementRegistrable {

	public static <P extends FoliagePlacer> FoliagePlacerType<P> createType(Codec<P> codec) {
		return FoliagePlacerTypeAccessor.create(codec);
	}

    private final AtomicBoolean registered = new AtomicBoolean(false);

	protected final IntProvider foliageHeight;

	protected static <P extends CustomFoliagePlacer> P3<Mu<P>, IntProvider, IntProvider, IntProvider> fillCustomFoliagePlacerFields(Instance<P> instance) {
		return fillFoliagePlacerFields(instance)
			.and(IntProvider.createValidatingCodec(1, 512).fieldOf("foliage_height").forGetter(placer -> placer.foliageHeight));
	}

    public CustomFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider foliageHeight) {
        super(radius, offset);
		this.foliageHeight = foliageHeight;
    }

	@Override
	public abstract FoliagePlacerType<?> getType();

    @Override
    public boolean isNotRegistered() {
        return !registered.get();
    }

    @Override
    public void setRegistered() {
        registered.set(true);
    }
}
