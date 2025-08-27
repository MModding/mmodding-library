package com.mmodding.library.item.mixin;

import com.mmodding.library.core.api.registry.StaticElement;
import com.mmodding.library.item.api.category.ItemCategory;
import com.mmodding.library.item.api.category.ItemCategoryContainer;
import com.mmodding.library.item.impl.category.ItemCategoryImpl;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public class ItemMixin implements StaticElement<Item>, ItemCategoryContainer {

	@Override
	public Registry<Item> mmodding$getRegistry() {
		return Registries.ITEM;
	}

	@Override
	public Item mmodding$as() {
		return (Item) (Object) this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Item> T setCategory(ItemCategory category) {
		ItemCategoryImpl.addEntries(category, this.mmodding$as().getDefaultStack());
		return (T) this.mmodding$as();
	}
}
