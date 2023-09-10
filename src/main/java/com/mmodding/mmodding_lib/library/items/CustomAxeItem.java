package com.mmodding.mmodding_lib.library.items;

import net.minecraft.item.AxeItem;
import net.minecraft.item.ToolMaterial;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomAxeItem extends AxeItem implements ItemRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	public CustomAxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
		super(material, attackDamage, attackSpeed, settings);
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
