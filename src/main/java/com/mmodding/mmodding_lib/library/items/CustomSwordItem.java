package com.mmodding.mmodding_lib.library.items;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomSwordItem extends SwordItem implements ItemRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	public CustomSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
		super(toolMaterial, attackDamage, attackSpeed, settings);
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
