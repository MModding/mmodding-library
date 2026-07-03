package com.mmodding.library.item.mixin;

import com.mmodding.library.item.api.properties.CustomItemProperty;
import com.mmodding.library.item.api.properties.MModdingItemProperties;
import com.mmodding.library.item.impl.property.CustomItemPropertyImpl;
import com.mmodding.library.item.impl.property.ItemPropertiesDuck;
import com.mmodding.library.java.api.list.BiList;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Item.Properties.class)
public class ItemPropertiesMixin implements MModdingItemProperties, ItemPropertiesDuck {

	@Unique
	private final BiList<Identifier, Object> customItemPropertiesQueue = BiList.create();

	@Override
	@SuppressWarnings("AddedMixinMembersNamePattern")
	public <T> Item.Properties custom(CustomItemProperty<T> property, T value) {
		this.customItemPropertiesQueue.add(property.getIdentifier(), value);
		return (Item.Properties) (Object) this;
	}

	@Override
	public void mmodding$applyCustomProperties(Item item) {
		this.customItemPropertiesQueue.forEach((property, value) ->
			CustomItemPropertyImpl.PROPERTIES_COMPANION.getOrCreateCompanion(item).register(property, value)
		);
		this.customItemPropertiesQueue.clear();
	}
}
