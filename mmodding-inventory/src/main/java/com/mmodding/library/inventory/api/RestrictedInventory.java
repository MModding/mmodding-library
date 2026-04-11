package com.mmodding.library.inventory.api;

import java.util.function.Predicate;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * An implementation of {@link Container} which allows restricting its usage by players with predicates.
 */
public interface RestrictedInventory extends BasicInventory {

	Predicate<Player> playerUsePredicate();

	static RestrictedInventory of(NonNullList<ItemStack> content, Predicate<Player> playerUsePredicate) {

		return new RestrictedInventory() {

			@Override
			public NonNullList<ItemStack> getContent() {
				return content;
			}

			@Override
			public Predicate<Player> playerUsePredicate() {
				return playerUsePredicate;
			}
		};
	}

	static RestrictedInventory ofSize(int size, Predicate<Player> playerUsePredicate) {
		return RestrictedInventory.of(NonNullList.withSize(size, ItemStack.EMPTY), playerUsePredicate);
	}

	/**
	 * Returns a new instance of the current inventory without its restrictions.
	 * @return a new basic inventory
	 */
	default BasicInventory avoid() {
		return BasicInventory.of(this.getContent());
	}

	@Override
	default boolean stillValid(Player player) {
		return this.playerUsePredicate().test(player);
	}
}
