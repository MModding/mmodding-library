package com.mmodding.library.energy.test.init;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.energy.test.block.entity.InfinitePowerSourceBlockEntity;
import com.mmodding.library.energy.test.block.entity.SuperMachineryBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class EnergyTestBlockEntities {

	public static final BlockEntityType<InfinitePowerSourceBlockEntity> INFINITE_POWER_SOURCE = FabricBlockEntityTypeBuilder.create(
		InfinitePowerSourceBlockEntity::new,
		EnergyTestBlocks.INFINITE_POWER_SOURCE
	).build();

	public static final BlockEntityType<SuperMachineryBlockEntity> SUPER_MACHINERY = FabricBlockEntityTypeBuilder.create(
		SuperMachineryBlockEntity::new,
		EnergyTestBlocks.SUPER_MACHINERY
	).build();

	public static void register(AdvancedContainer mod) {
		mod.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, "infinite_power_source", INFINITE_POWER_SOURCE);
		mod.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, "super_machinery", SUPER_MACHINERY);
	}
}
