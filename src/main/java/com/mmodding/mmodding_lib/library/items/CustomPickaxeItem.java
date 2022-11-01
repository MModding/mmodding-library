package com.mmodding.mmodding_lib.library.items;

import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomPickaxeItem extends PickaxeItem implements ItemRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	public CustomPickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
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
