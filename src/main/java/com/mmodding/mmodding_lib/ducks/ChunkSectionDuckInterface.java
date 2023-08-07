package com.mmodding.mmodding_lib.ducks;

import net.minecraft.world.World;

public interface ChunkSectionDuckInterface {

    World mmodding_lib$getWorld();

    void mmodding_lib$setWorld(World world);

    void mmodding_lib$reloadBiomeContainer();
}
