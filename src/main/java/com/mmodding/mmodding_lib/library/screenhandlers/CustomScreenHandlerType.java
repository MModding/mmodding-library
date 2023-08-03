package com.mmodding.mmodding_lib.library.screenhandlers;

import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomScreenHandlerType<T extends ScreenHandler> extends ScreenHandlerType<T> implements ScreenHandlerTypeRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	public CustomScreenHandlerType(Factory<T> factory) {
		super(factory);
	}

	@Override
	public boolean isNotRegistered() {
		return !this.registered.get();
	}

	@Override
	public void setRegistered() {
		this.registered.set(true);
	}
}
