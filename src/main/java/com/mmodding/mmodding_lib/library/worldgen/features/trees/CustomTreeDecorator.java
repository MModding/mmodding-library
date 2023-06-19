package com.mmodding.mmodding_lib.library.worldgen.features.trees;

import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class CustomTreeDecorator extends TreeDecorator implements TreeElementRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

    public CustomTreeDecorator() {
	}

	@Override
	public abstract TreeDecoratorType<?> getType();

	@Override
    public boolean isNotRegistered() {
        return !registered.get();
    }

    @Override
    public void setRegistered() {
        registered.set(true);
    }
}
