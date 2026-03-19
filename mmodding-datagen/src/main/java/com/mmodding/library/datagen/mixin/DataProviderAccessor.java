package com.mmodding.library.datagen.mixin;

import net.minecraft.data.DataProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Comparator;

@Mixin(DataProvider.class)
public interface DataProviderAccessor {

	@Accessor("JSON_KEY_SORTING_COMPARATOR")
	static Comparator<String> mmodding$getSortingComparator() {
		throw new IllegalStateException();
	}
}
