package com.mmodding.library.item.impl.category;

import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.core.impl.PostContent;
import com.mmodding.library.item.api.category.ItemCategory;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
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
	private static final LiteRegistry<CreativeModeTab> GROUPS = LiteRegistry.create();

	private final ResourceKey<CreativeModeTab> key;
	private final SettingsImpl settings;
	private final Set<ItemStack> entries = new HashSet<>();

	public ItemCategoryImpl(ResourceKey<CreativeModeTab> key, Consumer<Settings> settings) {
		this.key = key;
		SettingsImpl toBePassed = new SettingsImpl();
		settings.accept(toBePassed);
		this.settings = toBePassed;
		ItemCategoryImpl.CATEGORIES.add(() -> this);
	}

	public static void addEntries(ItemCategory category, ItemStack... entries) {
		((ItemCategoryImpl) category).entries.addAll(Arrays.stream(entries).collect(Collectors.toSet()));
	}

	public void init() {
		CreativeModeTab.Builder builder = FabricCreativeModeTab.builder();
		if (this.settings.title != null) {
			builder.title(this.settings.title);
		}
		if (this.settings.icon != null) {
			builder.icon(this.settings.icon);
		}
		if (this.settings.alignedRight) {
			builder.alignedRight();
		}
		if (this.settings.hideTitle) {
			builder.hideTitle();
		}
		if (this.settings.noScrollbar) {
			builder.noScrollBar();
		}
		if (this.settings.backgroundTexture != null) {
			builder.backgroundTexture(this.settings.backgroundTexture);
		}
		builder.displayItems((parameters, collector) -> collector.acceptAll(this.entries));
		ItemCategoryImpl.GROUPS.register(this.key.identifier(), builder.build()); // replaces the future registry
	}

	@Override
	public ResourceKey<CreativeModeTab> getRegistryKey() {
		return this.key;
	}

	@Override
	public Optional<CreativeModeTab> getCreativeModeTab() {
		if (ItemCategoryImpl.GROUPS.contains(this.key.identifier())) {
			return Optional.of(ItemCategoryImpl.GROUPS.get(this.key.identifier()));
		}
		else {
			return Optional.empty();
		}
	}

	public static class SettingsImpl implements Settings {

		private Component title;
		private Supplier<ItemStack> icon;
		private boolean alignedRight;
		private boolean hideTitle;
		private boolean noScrollbar;
		private Identifier backgroundTexture;

		@Override
		public Settings title(Component name) {
			this.title = name;
			return this;
		}

		@Override
		public Settings icon(Supplier<ItemStack> iconSupplier) {
			this.icon = iconSupplier;
			return this;
		}

		@Override
		public Settings alignRight() {
			this.alignedRight = true;
			return this;
		}

		@Override
		public Settings hideTitle() {
			this.hideTitle = true;
			return this;
		}

		@Override
		public Settings noScrollbar() {
			this.noScrollbar = true;
			return this;
		}

		@Override
		public Settings backgroundTexture(Identifier backgroundTexture) {
			this.backgroundTexture = backgroundTexture;
			return this;
		}
	}

	static {
		PostContent.POST_CONTENT.register(() -> CATEGORIES.forEach(supplier -> supplier.get().init()));
	}
}
