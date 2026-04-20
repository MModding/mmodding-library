package com.mmodding.library.item.mixin;

import com.mmodding.library.item.api.properties.CustomItemProperty;
import com.mmodding.library.item.api.properties.MModdingItemProperties;
import com.mmodding.library.item.impl.setting.CustomItemPropertyImpl;
import com.mmodding.library.java.api.list.BiList;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.Properties.class)
public class ItemPropertiesMixin implements MModdingItemProperties {

	@Shadow
	private @Nullable ResourceKey<Item> id;

	@Unique
	private final BiList<Identifier, Object> customItemPropertiesQueue = BiList.create();

	@Inject(method = "setId", at = @At("TAIL"))
	private void dequeueAll(ResourceKey<Item> id, CallbackInfoReturnable<Item.Properties> cir) {
		this.customItemPropertiesQueue.forEach((property, value) ->
			CustomItemPropertyImpl.REGISTRY.getOrCreateCompanion(this.id).register(property, value)
		);
		this.customItemPropertiesQueue.clear();
	}

	@Override
	@SuppressWarnings("AddedMixinMembersNamePattern")
	public <T> Item.Properties custom(CustomItemProperty<T> property, T value) {
		if (this.id == null) {
			this.customItemPropertiesQueue.add(property.getIdentifier(), value);
		}
		else {
			CustomItemPropertyImpl.REGISTRY.getOrCreateCompanion(this.id).register(property.getIdentifier(), value);
		}
		return (Item.Properties) (Object) this;
	}
}
