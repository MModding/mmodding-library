package com.mmodding.library.item.api.group;

import com.mmodding.library.core.api.Reference;
import com.mmodding.library.item.impl.group.ItemGroupQualifierImpl;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Consumer;
import java.util.function.Supplier;

@ApiStatus.NonExtendable
public interface ItemGroupQualifier {

	static ItemGroupQualifier create(Reference<ItemGroup> reference, Consumer<Settings> settings) {
		return new ItemGroupQualifierImpl(reference, settings);
	}

	Reference<ItemGroup> getReference();

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
