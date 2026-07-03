package com.mmodding.library.item.mixin;

import com.mmodding.library.item.impl.property.ItemPropertiesDuck;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public class ItemMixin {

	@Inject(method = "<init>", at = @At("TAIL"))
	private void applyCustomProperties(Item.Properties properties, CallbackInfo ci) {
		((ItemPropertiesDuck) properties).mmodding$applyCustomProperties((Item) (Object) this);
	}
}
