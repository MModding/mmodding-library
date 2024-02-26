package com.mmodding.mmodding_lib.library.items;

import com.mmodding.mmodding_lib.library.utils.FilterList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ClickType;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class CustomItemAcceptingStacks extends Item implements ItemRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

    public CustomItemAcceptingStacks(Settings settings) {
		super(settings);
	}

	@Override
	public boolean onClickedOnOther(ItemStack thisStack, Slot otherSlot, ClickType clickType, PlayerEntity player) {
		if (clickType != ClickType.RIGHT) {
			return false;
		} else {
			ItemStack itemStack = otherSlot.getStack();
			if (this.getFilter().check(itemStack.getItem())) {
				boolean sound = otherSlot.getStack().getCount() > 0;
				this.receiveStack(otherSlot.takeStack(1));
				if (sound) {
					this.playReceiveStackSound(player);
				}
			}

			return true;
		}
	}

	@Override
	public boolean onClicked(ItemStack thisStack, ItemStack otherStack, Slot thisSlot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
		if (clickType == ClickType.RIGHT && thisSlot.canTakePartial(player)) {
			if (this.getFilter().check(otherStack.getItem())) {
				boolean sound = otherStack.getCount() > 0;
				if (sound) {
					this.receiveStack(otherStack);
					otherStack.decrement(otherStack.getCount());
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public abstract void receiveStack(ItemStack stack);

	public abstract void playReceiveStackSound(PlayerEntity player);

	public abstract FilterList<Item> getFilter();

	@Override
    public boolean isNotRegistered() {
        return !this.registered.get();
    }

    @Override
    public void setRegistered() {
        this.registered.set(true);
    }
}
