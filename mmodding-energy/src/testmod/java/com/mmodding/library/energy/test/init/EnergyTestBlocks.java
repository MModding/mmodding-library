package com.mmodding.library.energy.test.init;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.energy.test.EnergyTests;
import com.mmodding.library.energy.test.block.SuperMachineryBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class EnergyTestBlocks {

	public static final Block SUPER_MACHINERY = Blocks.register(EnergyTests.createKey(Registries.BLOCK, "super_machinery"), SuperMachineryBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE));

	public static void register(AdvancedContainer mod) {}
}
