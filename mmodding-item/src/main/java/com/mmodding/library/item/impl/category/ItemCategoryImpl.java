package com.mmodding.library.item.impl.category;

import com.mmodding.library.core.api.Reference;
import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.core.impl.PostContent;
import com.mmodding.library.item.api.category.ItemCategory;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@ApiStatus.Internal
public class ItemCategoryImpl implements ItemCategory {

	private static final Set<Supplier<ItemCategoryImpl>> CATEGORIES = new HashSet<>();
	private static final LiteRegistry<ItemGroup> GROUPS = LiteRegistry.create();

	private final Reference<ItemGroup> reference;
	private final SettingsImpl settings;
	private final Set<ItemStack> entries = new HashSet<>();

	public ItemCategoryImpl(Reference<ItemGroup> reference, Consumer<Settings> settings) {
		this.reference = reference;
		SettingsImpl toBePassed = new SettingsImpl();
		settings.accept(toBePassed);
		this.settings = toBePassed;
		ItemCategoryImpl.CATEGORIES.add(() -> this);
	}

	public static void addEntries(ItemCategory category, ItemStack... entries) {
		((ItemCategoryImpl) category).entries.addAll(Arrays.stream(entries).collect(Collectors.toSet()));
	}

	public void init() {
		ItemGroup.Builder builder = FabricItemGroup.builder();
		if (this.settings.name != null) {
			builder.displayName(this.settings.name);
		}
		if (this.settings.iconSupplier != null) {
			builder.icon(this.settings.iconSupplier);
		}
		if (this.settings.special) {
			builder.special();
		}
		if (this.settings.hideName) {
			builder.noRenderedName();
		}
		if (this.settings.hideScrollbar) {
			builder.noScrollbar();
		}
		if (this.settings.textureName != null) {
			builder.texture(this.settings.textureName);
		}
		builder.entries((parameters, collector) -> collector.addAll(this.entries));
		ItemCategoryImpl.GROUPS.register(this.reference.provideId(), builder.build()); // replaces the future registry
	}

	@Override
	public Reference<ItemGroup> getReference() {
		return this.reference;
	}

	@Override
	public Optional<ItemGroup> getItemGroup() {
		if (ItemCategoryImpl.GROUPS.contains(this.reference.provideId())) {
			return Optional.of(ItemCategoryImpl.GROUPS.getEntry(this.reference.provideId()));
		}
		else {
			return Optional.empty();
		}
	}

	public static class SettingsImpl implements Settings {

		private Text name;
		private Supplier<ItemStack> iconSupplier;
		private boolean special;
		private boolean hideName;
		private boolean hideScrollbar;
		private String textureName;

		@Override
		public Settings name(Text name) {
			this.name = name;
			return this;
		}

		@Override
		public Settings icon(Supplier<ItemStack> iconSupplier) {
			this.iconSupplier = iconSupplier;
			return this;
		}

		@Override
		public Settings special() {
			this.special = true;
			return this;
		}

		@Override
		public Settings hideName() {
			this.hideName = true;
			return this;
		}

		@Override
		public Settings hideScrollbar() {
			this.hideScrollbar = true;
			return this;
		}

		@Override
		public Settings backgroundTextureName(String textureName) {
			this.textureName = textureName;
			return this;
		}
	}

	static {
		PostContent.POST_CONTENT.register(() -> CATEGORIES.forEach(supplier -> supplier.get().init()));
	}
}
