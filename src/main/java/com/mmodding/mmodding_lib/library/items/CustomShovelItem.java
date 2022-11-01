package com.mmodding.mmodding_lib.library.items;

import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomShovelItem extends ShovelItem implements ItemRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	public CustomShovelItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
		super(material, attackDamage, attackSpeed, settings);
	}

	@Override
	public boolean isNotRegistered() {
		return !registered.get();
	}

	@Override
	public void setRegistered() {
		registered.set(true);
	}
}
