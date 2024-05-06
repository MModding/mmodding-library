package com.mmodding.library.core.impl;

import org.jetbrains.annotations.ApiStatus;
import org.quiltmc.qsl.base.api.event.Event;

@ApiStatus.Internal
public class PostContent {

	public static final Event<Runnable> POST_CONTENT = Event.create(Runnable.class, runnables -> () -> {
		for (Runnable runnable : runnables) {
			runnable.run();
		}
	});
}
