package com.mmodding.library.item.api.category;

import com.mmodding.library.core.api.Reference;
import com.mmodding.library.item.impl.category.ItemCategoryImpl;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@ApiStatus.NonExtendable
public interface ItemCategory {

	static ItemCategory create(Reference<ItemGroup> reference, Consumer<Settings> settings) {
		return new ItemCategoryImpl(reference, settings);
	}

	Reference<ItemGroup> getReference();

	Optional<ItemGroup> getItemGroup();

	@ApiStatus.NonExtendable
	interface Settings {

		Settings name(Text name);

		Settings icon(Supplier<ItemStack> iconSupplier);

		Settings special();

		Settings hideName();

		Settings hideScrollbar();

		Settings backgroundTextureName(String textureName);
	}
}
