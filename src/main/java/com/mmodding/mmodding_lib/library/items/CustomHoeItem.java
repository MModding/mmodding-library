package com.mmodding.mmodding_lib.library.items;

import net.minecraft.item.HoeItem;
import net.minecraft.item.ToolMaterial;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomHoeItem extends HoeItem implements ItemRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	protected CustomHoeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
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
