package com.mmodding.mmodding_lib.mixin.accessors;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TrunkPlacerType.class)
public interface TrunkPlacerTypeAccessor {

	@Invoker("<init>")
	static <P extends TrunkPlacer> TrunkPlacerType<P> create(Codec<P> codec) {
		return null;
	}
}
