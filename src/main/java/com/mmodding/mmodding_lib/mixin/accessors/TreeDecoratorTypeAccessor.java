package com.mmodding.mmodding_lib.mixin.accessors;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TreeDecoratorType.class)
public interface TreeDecoratorTypeAccessor {

	@Invoker("<init>")
	static <P extends TreeDecorator> TreeDecoratorType<P> create(Codec<P> codec) {
		return null;
	}
}
