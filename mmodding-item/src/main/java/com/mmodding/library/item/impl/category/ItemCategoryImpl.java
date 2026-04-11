package com.mmodding.library.item.impl.category;

import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.core.impl.PostContent;
import com.mmodding.library.item.api.category.ItemCategory;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.network.chat.Component;
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
		CreativeModeTab.Builder builder = FabricItemGroup.builder();
		if (this.settings.name != null) {
			builder.title(this.settings.name);
		}
		if (this.settings.iconSupplier != null) {
			builder.icon(this.settings.iconSupplier);
		}
		if (this.settings.special) {
			builder.alignedRight();
		}
		if (this.settings.hideName) {
			builder.hideTitle();
		}
		if (this.settings.hideScrollbar) {
			builder.noScrollBar();
		}
		if (this.settings.textureName != null) {
			builder.backgroundSuffix(this.settings.textureName);
		}
		builder.displayItems((parameters, collector) -> collector.acceptAll(this.entries));
		ItemCategoryImpl.GROUPS.register(this.key.location(), builder.build()); // replaces the future registry
	}

	@Override
	public ResourceKey<CreativeModeTab> getRegistryKey() {
		return this.key;
	}

	@Override
	public Optional<CreativeModeTab> getCreativeModeTab() {
		if (ItemCategoryImpl.GROUPS.contains(this.key.location())) {
			return Optional.of(ItemCategoryImpl.GROUPS.get(this.key.location()));
		}
		else {
			return Optional.empty();
		}
	}

	public static class SettingsImpl implements Settings {

		private Component name;
		private Supplier<ItemStack> iconSupplier;
		private boolean special;
		private boolean hideName;
		private boolean hideScrollbar;
		private String textureName;

		@Override
		public Settings name(Component name) {
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
