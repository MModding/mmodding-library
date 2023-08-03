package com.mmodding.mmodding_lib.library.worldgen.features.trees;

import com.mmodding.mmodding_lib.mixin.accessors.TreeDecoratorTypeAccessor;
import com.mojang.serialization.Codec;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class CustomTreeDecorator extends TreeDecorator implements TreeElementRegistrable {

	public static <P extends TreeDecorator> TreeDecoratorType<P> createType(Codec<P> codec) {
		return TreeDecoratorTypeAccessor.create(codec);
	}

    private final AtomicBoolean registered = new AtomicBoolean(false);

    public CustomTreeDecorator() {
	}

	@Override
	public abstract TreeDecoratorType<?> getType();

	@Override
    public boolean isNotRegistered() {
        return !this.registered.get();
    }

    @Override
    public void setRegistered() {
		this.registered.set(true);
    }
}
