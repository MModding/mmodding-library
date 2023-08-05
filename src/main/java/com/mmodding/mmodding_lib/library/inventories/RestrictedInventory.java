package com.mmodding.mmodding_lib.library.inventories;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

import java.util.function.Predicate;

public interface RestrictedInventory extends BasicInventory {

	Predicate<PlayerEntity> playerUsePredicate();

	static RestrictedInventory of(DefaultedList<ItemStack> content, Predicate<PlayerEntity> playerUsePredicate) {

		return new RestrictedInventory() {

			@Override
			public DefaultedList<ItemStack> getContent() {
				return content;
			}

			@Override
			public Predicate<PlayerEntity> playerUsePredicate() {
				return playerUsePredicate;
			}
		};
	}

	static RestrictedInventory ofSize(int size, Predicate<PlayerEntity> playerUsePredicate) {
		return RestrictedInventory.of(DefaultedList.ofSize(size, ItemStack.EMPTY), playerUsePredicate);
	}

	default BasicInventory avoid() {
		return BasicInventory.of(this.getContent());
	}

	@Override
	default boolean canPlayerUse(PlayerEntity player) {
		return this.playerUsePredicate().test(player);
	}
}
