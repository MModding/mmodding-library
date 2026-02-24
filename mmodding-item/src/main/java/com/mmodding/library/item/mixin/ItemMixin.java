package com.mmodding.library.item.mixin;

import com.mmodding.library.item.api.category.ItemCategory;
import com.mmodding.library.item.api.category.ItemCategoryContainer;
import com.mmodding.library.item.impl.category.ItemCategoryImpl;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Item.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public abstract class ItemMixin implements ItemCategoryContainer {

	@Shadow
	public abstract ItemStack getDefaultStack();

	@Override
	@SuppressWarnings({"unchecked", "DataFlowIssue"})
	public <T extends Item> T setCategory(ItemCategory category) {
		ItemCategoryImpl.addEntries(category, this.getDefaultStack());
		return (T) (Object) this;
	}
}
