package com.mmodding.library.block.api.event;

import com.mmodding.library.core.api.MModdingLibrary;
import dev.yumi.commons.event.Event;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Provides common block events about level operations.
 */
public class LevelBlockEvents {

	private LevelBlockEvents() {}

	/**
	 * @see DirtyChange
	 */
	public static final Event<Identifier, DirtyChange> DIRTY_CHANGE = MModdingLibrary.getEventManager().create(DirtyChange.class);

	@FunctionalInterface
	public interface DirtyChange {

		/**
		 * Triggers after a block state is changed to a different block state.
		 * @param level the level
		 * @param pos the position
		 * @param oldState the old block state
		 * @param newState the new block state
		 */
		void onDirtyChange(Level level, BlockPos pos, BlockState oldState, BlockState newState);
	}
}
