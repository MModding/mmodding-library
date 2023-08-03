package com.mmodding.mmodding_lib.library.items;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.SpawnEggItem;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomSpawnEggItem extends SpawnEggItem implements ItemRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

    public CustomSpawnEggItem(EntityType<? extends MobEntity> entityType, int primaryColor, int secondaryColor, Settings settings) {
        super(entityType, primaryColor, secondaryColor, settings);
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
