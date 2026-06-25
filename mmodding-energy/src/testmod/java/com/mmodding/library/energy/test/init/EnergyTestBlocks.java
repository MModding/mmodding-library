package com.mmodding.library.energy.test.init;

import com.mmodding.library.block.api.BlockWithItem;
import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.energy.test.EnergyTests;
import com.mmodding.library.energy.test.block.SuperMachineryBlock;
import com.mmodding.library.energy.test.block.InfinitePowerSourceBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class EnergyTestBlocks {

	public static final Block INFINITE_POWER_SOURCE = ((BlockWithItem) Blocks.register(EnergyTests.createKey(Registries.BLOCK, "infinite_power_source"), InfinitePowerSourceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))).registerItem();
	public static final Block SUPER_MACHINERY = ((BlockWithItem) Blocks.register(EnergyTests.createKey(Registries.BLOCK, "super_machinery"), SuperMachineryBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.STONE))).registerItem();

	public static void register(AdvancedContainer mod) {}
}
