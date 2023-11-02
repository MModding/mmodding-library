package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Holder;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Set;

@Mixin(PointOfInterestTypes.class)
public interface PointOfInterestTypesAccessor {

	@Invoker("states")
	static Set<BlockState> invokeStates(Block block) {
		throw new AssertionError();
	}

	@Invoker("addStates")
	static void invokeAddStates(Holder<PointOfInterestType> holder) {
		throw new AssertionError();
	}
}
