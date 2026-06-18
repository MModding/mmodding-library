package com.mmodding.library.inventory.api;

import java.util.function.Predicate;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * An implementation of {@link Container} which allows restricting its usage by players with predicates.
 */
public abstract class RestrictedInventory extends SimpleContainer {

	private final Predicate<Player> playerUsePredicate;

	public RestrictedInventory(Predicate<Player> playerUsePredicate, int size) {
		super(size);
		this.playerUsePredicate = playerUsePredicate;
	}

	public RestrictedInventory(Predicate<Player> playerUsePredicate, ItemStack... stacks) {
		super(stacks);
		this.playerUsePredicate = playerUsePredicate;
	}

	@Override
	public boolean stillValid(Player player) {
		return this.playerUsePredicate.test(player);
	}
}
