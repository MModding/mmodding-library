package com.mmodding.library.inventory.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

import java.util.function.Predicate;

/**
 * An implementation of {@link Inventory} which allows restricting its usage by players with predicates.
 */
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

	/**
	 * Returns a new instance of the current inventory without its restrictions.
	 * @return a new basic inventory
	 */
	default BasicInventory avoid() {
		return BasicInventory.of(this.getContent());
	}

	@Override
	default boolean canPlayerUse(PlayerEntity player) {
		return this.playerUsePredicate().test(player);
	}
}
