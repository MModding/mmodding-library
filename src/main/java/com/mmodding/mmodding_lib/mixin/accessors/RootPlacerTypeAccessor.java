package com.mmodding.mmodding_lib.mixin.accessors;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.root.RootPlacer;
import net.minecraft.world.gen.root.RootPlacerType;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RootPlacerType.class)
public interface RootPlacerTypeAccessor {

	@Invoker("<init>")
	static <P extends RootPlacer> RootPlacerType<P> create(Codec<P> codec) {
		return null;
	}
}
