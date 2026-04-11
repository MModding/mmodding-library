package com.mmodding.library.core.impl;

import com.mmodding.library.core.api.MModdingLibrary;
import dev.yumi.commons.event.Event;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class PostContent {

	// Running After Most "Content" Initialization
	public static final Event<ResourceLocation, Runnable> POST_CONTENT = MModdingLibrary.getEventManager().create(Runnable.class);
}
