package com.mmodding.mmodding_lib.library.screenhandlers;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.screen.ScreenHandler;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomExtendedScreenHandlerType<T extends ScreenHandler> extends ExtendedScreenHandlerType<T> implements ScreenHandlerTypeRegistrable {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	public CustomExtendedScreenHandlerType(ExtendedFactory<T> factory) {
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
