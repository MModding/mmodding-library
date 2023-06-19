package com.mmodding.mmodding_lib.mixin.accessors;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FoliagePlacerType.class)
public interface FoliagePlacerTypeAccessor {

	@Invoker("<init>")
	static <P extends FoliagePlacer> FoliagePlacerType<P> create(Codec<P> codec) {
		return null;
	}
}
