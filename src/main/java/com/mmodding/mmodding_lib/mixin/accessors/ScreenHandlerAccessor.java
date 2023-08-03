package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ScreenHandler.class)
public interface ScreenHandlerAccessor {

	@Invoker("addSlot")
	Slot addSlot(Slot slot);
}
