package com.mmodding.mmodding_lib.library.portals;

import com.mmodding.mmodding_lib.library.items.ItemRegistrable;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomPortalKeyItem extends Item implements CustomPortalKey, ItemRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

	private final SoundEvent soundEvent;

    public CustomPortalKeyItem(Settings settings, SoundEvent soundEvent) {
        super(settings);
		this.soundEvent = soundEvent;
    }

	@Override
	public Item getItem() {
		return this;
	}

	@Override
	public SoundEvent getIgniteSound() {
		return this.soundEvent;
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
