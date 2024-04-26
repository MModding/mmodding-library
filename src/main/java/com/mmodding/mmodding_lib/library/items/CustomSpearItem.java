package com.mmodding.mmodding_lib.library.items;

import com.mmodding.mmodding_lib.library.entities.projectiles.SpearEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.world.World;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomSpearItem extends TridentItem implements ItemRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

	private final LaunchFactory<? extends SpearEntity> launchFactory;

	public CustomSpearItem(Settings settings) {
		this(null, settings);
	}

    public CustomSpearItem(LaunchFactory<? extends SpearEntity> launchFactory, Settings settings) {
		super(settings);
	    this.launchFactory = launchFactory;
    }

    @Override
    public boolean isNotRegistered() {
        return !this.registered.get();
    }

    @Override
    public void setRegistered() {
        this.registered.set(true);
    }

	public LaunchFactory<?> getLaunchFactory() {
		return this.launchFactory;
	}

	@FunctionalInterface
	public interface LaunchFactory<T extends SpearEntity> {

		T create(World world, LivingEntity owner, ItemStack stack);
	}
}
