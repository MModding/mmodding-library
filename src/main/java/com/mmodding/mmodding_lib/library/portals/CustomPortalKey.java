package com.mmodding.mmodding_lib.library.portals;

import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;

public interface CustomPortalKey {

	static CustomPortalKey ofVanilla(Item item, SoundEvent soundEvent) {

		return new CustomPortalKey() {

			@Override
			public Item getItem() {
				return item;
			}

			@Override
			public SoundEvent getIgniteSound() {
				return soundEvent;
			}
		};
	}

	Item getItem();

	SoundEvent getIgniteSound();
}
