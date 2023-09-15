package com.mmodding.mmodding_lib.library.fluids.buckets;

import com.mmodding.mmodding_lib.library.items.ItemRegistrable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomBucketItem extends BucketItem implements ItemRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

	private final BucketManager manager;

    public CustomBucketItem(Fluid fluid, Settings settings) {
        this(fluid, BucketManager.DEFAULT, settings);
    }

	public CustomBucketItem(Fluid fluid, BucketManager manager, Settings settings) {
		super(fluid, settings);
		this.manager = manager;
	}

	public BucketManager getManager() {
		return this.manager;
	}

	public ItemStack getRemainderStack(ItemStack stack, PlayerEntity player) {
		return !player.getAbilities().creativeMode ? this.getManager().getEmptiedItem(stack) : stack;
	}

    @Override
    public boolean isNotRegistered() {
        return !this.registered.get();
    }

    @Override
    public void setRegistered() {
        this.registered.set(true);
    }
}
