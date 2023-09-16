package com.mmodding.mmodding_lib.library.fluids.buckets;

import com.mmodding.mmodding_lib.library.items.ItemRegistrable;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PowderSnowBucketItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomPowderSnowBucketItem extends PowderSnowBucketItem implements ItemRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

	private final BucketManager manager;

    public CustomPowderSnowBucketItem(Block block, SoundEvent placeSound, Settings settings) {
        this(block, placeSound, BucketManager.DEFAULT, settings);
    }

	public CustomPowderSnowBucketItem(Block block, SoundEvent placeSound, BucketManager manager, Settings settings) {
		super(block, placeSound, settings);
		this.manager = manager;
	}

	public BucketManager getManager() {
		return this.manager;
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		ActionResult actionResult = super.useOnBlock(context);
		PlayerEntity player = context.getPlayer();
		if (actionResult.isAccepted() && player != null && !player.isCreative()) {
			Hand hand = context.getHand();
			player.setStackInHand(hand, this.getManager().getEmptiedItem(context.getStack()));
		}
		return actionResult;
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
