package com.mmodding.mmodding_lib.library.enchantments;

import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;
import com.mmodding.mmodding_lib.library.items.ItemRegistrable;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomEnchantedBookItem extends EnchantedBookItem implements ItemRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	private EnchantmentType type;

	public CustomEnchantedBookItem(AdvancedItemSettings settings) {
		super(settings);
	}

	public void setType(EnchantmentType type) {
		this.type = type;
	}

	public ItemStack forCustomEnchantment(EnchantmentLevelEntry info) {
		ItemStack stack = new ItemStack(this.type.bookItem());
		stack.setCustomName(Text.of(this.type.prefix() + " " + stack.getName()));
		addEnchantment(stack, info);
		return stack;
	}

	@Override
	public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
		if (group == ItemGroup.SEARCH) {
			for(Enchantment enchantment : Registry.ENCHANTMENT) {
				if (enchantment.type != null) {
					if (enchantment instanceof CustomEnchantment customEnchantment) {
						if (customEnchantment.getType() == this.type) {
							for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) {
								stacks.add(this.forCustomEnchantment(new EnchantmentLevelEntry(enchantment, i)));
							}
						}
					}
				}
			}
		} else if (group.getEnchantments().length != 0) {
			for(Enchantment enchantment : Registry.ENCHANTMENT) {
				if (group.containsEnchantments(enchantment.type)) {
					if (enchantment instanceof CustomEnchantment customEnchantment) {
						if (customEnchantment.getType() == this.type) {
							stacks.add(this.forCustomEnchantment(new EnchantmentLevelEntry(enchantment, enchantment.getMaxLevel())));
						}
					}
				}
			}
		}
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
