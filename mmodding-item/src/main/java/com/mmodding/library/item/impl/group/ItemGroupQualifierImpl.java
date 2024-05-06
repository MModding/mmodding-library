package com.mmodding.library.item.impl.group;

import com.mmodding.library.core.api.Reference;
import com.mmodding.library.core.impl.PostContent;
import com.mmodding.library.item.api.group.ItemGroupQualifier;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@ApiStatus.Internal
public class ItemGroupQualifierImpl implements ItemGroupQualifier {

	private static final Set<Supplier<ItemGroupQualifierImpl>> QUALIFIERS = new HashSet<>();

	private final Reference<ItemGroup> reference;
	private final SettingsImpl settings;
	private final Set<ItemStack> entries = new HashSet<>();

	public ItemGroupQualifierImpl(Reference<ItemGroup> reference, Consumer<Settings> settings) {
		this.reference = reference;
		SettingsImpl toBePassed = new SettingsImpl();
		settings.accept(toBePassed);
		this.settings = toBePassed;
		ItemGroupQualifierImpl.QUALIFIERS.add(() -> this);
	}

	public static void addEntries(ItemGroupQualifier qualifier, ItemStack... entries) {
		((ItemGroupQualifierImpl) qualifier).entries.addAll(Arrays.stream(entries).collect(Collectors.toSet()));
	}

	public void init() {
		ItemGroup.Builder builder = FabricItemGroup.builder(this.reference.provideId());
		if (this.settings.name != null) {
			builder.name(this.settings.name);
		}
		if (this.settings.iconSupplier != null) {
			builder.icon(this.settings.iconSupplier);
		}
		if (this.settings.special) {
			builder.special();
		}
		if (this.settings.hideName) {
			builder.hideName();
		}
		if (this.settings.hideScrollbar) {
			builder.hideScrollbar();
		}
		if (this.settings.textureName != null) {
			builder.backgroundTextureName(this.settings.textureName);
		}
		builder.entries((parameters, collector) -> collector.addStacks(this.entries));
	}

	@Override
	public Reference<ItemGroup> getReference() {
		return this.reference;
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
		PostContent.POST_CONTENT.register(() -> {
			QUALIFIERS.forEach(supplier -> supplier.get().init());
		});
	}
}
