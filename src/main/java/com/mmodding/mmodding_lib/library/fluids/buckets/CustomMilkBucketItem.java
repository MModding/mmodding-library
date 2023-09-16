package com.mmodding.mmodding_lib.library.fluids.buckets;

import com.mmodding.mmodding_lib.library.items.ItemRegistrable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomMilkBucketItem extends MilkBucketItem implements ItemRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

	private final BucketManager manager;

    public CustomMilkBucketItem(Settings settings) {
        this(BucketManager.DEFAULT, settings);
    }

	public CustomMilkBucketItem(BucketManager manager, Settings settings) {
		super(settings);
		this.manager = manager;
	}

	public BucketManager getManager() {
		return this.manager;
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (user instanceof ServerPlayerEntity player) {
			Criteria.CONSUME_ITEM.trigger(player, stack);
			player.incrementStat(Stats.USED.getOrCreateStat(this));
		}

		if (user instanceof PlayerEntity player && !player.getAbilities().creativeMode) {
			stack.decrement(1);
		}

		if (!world.isClient) {
			user.clearStatusEffects();
		}

		return stack.isEmpty() ? this.getManager().getEmptiedItem(stack) : stack;
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
