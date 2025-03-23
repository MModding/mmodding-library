package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.worldgen.structures.StructureSpreadLoot;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.ServerWorldAccess;

public interface StructureStartDuckInterface {

	void mmodding_lib$injectProvider(StructureSpreadLoot.StructureSpreadLootProvider provider);

	void mmodding_lib$spreadLoots(ServerWorldAccess world, RandomGenerator random);
}
