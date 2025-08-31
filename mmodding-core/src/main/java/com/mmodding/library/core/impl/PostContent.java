package com.mmodding.library.core.impl;

import com.mmodding.library.core.api.MModdingLibrary;
import dev.yumi.commons.event.Event;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class PostContent {

	// Running After Most "Content" Initialization
	public static final Event<Identifier, Runnable> POST_CONTENT = MModdingLibrary.getEventManager().create(Runnable.class);
}
