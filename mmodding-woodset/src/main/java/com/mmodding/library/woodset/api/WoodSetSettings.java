package com.mmodding.library.woodset.api;

import com.mmodding.library.woodset.impl.WoodSetSettingsImpl;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Supplier;

/**
 * An interface used in {@link WoodSet} to provide additional information used in other systems, such as datagen.
 */
public interface WoodSetSettings {

	WoodSetSettings DEFAULT = create(true, LogDisplay.WITH_HORIZONTAL);

	static WoodSetSettings create(boolean burnable, LogDisplay logDisplay) {
		return WoodSetSettings.create(burnable, logDisplay, () -> Blocks.IRON_CHAIN);
	}

	static WoodSetSettings create(boolean burnable, LogDisplay logDisplay, Supplier<Block> hangingSignChain) {
		return new WoodSetSettingsImpl(burnable, logDisplay, hangingSignChain);
	}

	boolean isBurnable();

	/**
	 * The {@link LogDisplay} indicates which model should be used for the log.
	 * <br>Defaults to {@link LogDisplay#WITH_HORIZONTAL}.
	 * @return the log display
	 */
	LogDisplay getLogDisplay();

	/**
	 * Indicates which {@link Block} to use as chains in the hanging sign's craft.
	 * @return the resource key
	 */
	Block getHangingSignChain();

	/**
	 * Indicates which model should be used for the log.
	 * This is useful for automated datagen.
	 */
	enum LogDisplay {
		NORMAL, // Like nether logs.
		WITH_HORIZONTAL, // Like most of vanilla logs.
		UV_LOCKED // Like cherry log and bamboo log.
	}
}
