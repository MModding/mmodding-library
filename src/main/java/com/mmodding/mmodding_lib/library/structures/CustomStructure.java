package com.mmodding.mmodding_lib.library.structures;

import com.mojang.serialization.Codec;
import net.minecraft.structure.PostPlacementProcessor;
import net.minecraft.structure.StructurePiecesGeneratorFactory;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomStructure<C extends FeatureConfig> extends StructureFeature<C> implements StructureRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

    public CustomStructure(Codec<C> codec, StructurePiecesGeneratorFactory<C> structurePiecesGeneratorFactory, PostPlacementProcessor postPlacementProcessor) {
        super(codec, structurePiecesGeneratorFactory, postPlacementProcessor);
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
