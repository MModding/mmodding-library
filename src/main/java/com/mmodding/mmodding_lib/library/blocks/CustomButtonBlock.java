package com.mmodding.mmodding_lib.library.blocks;

import net.minecraft.block.StoneButtonBlock;
import net.minecraft.block.WoodenButtonBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomButtonBlock {

	public static class Wooden extends WoodenButtonBlock implements BlockRegistrable, BlockWithItem {

		private final AtomicBoolean registered = new AtomicBoolean(false);

		private BlockItem item = null;

		public Wooden(Settings settings) {
			this(settings, false);
		}

		public Wooden(Settings settings, boolean hasItem) {
			this(settings, hasItem, (ItemGroup) null);
		}

		public Wooden(Settings settings, boolean hasItem, ItemGroup itemGroup) {
			this(settings, hasItem, itemGroup != null ? new QuiltItemSettings().group(itemGroup) : new QuiltItemSettings());
		}

		public Wooden(Settings settings, boolean hasItem, Item.Settings itemSettings) {
			super(settings);
			if (hasItem) this.item = new BlockItem(this, itemSettings);
		}

		@Override
		public BlockItem getItem() {
			return this.item;
		}

		@Override
		public boolean isNotRegistered() {
			return !this.registered.get();
		}

		@Override
		public void setRegistered() {
			this.registered.set(true);
		}
	}

	public static class Stone extends StoneButtonBlock implements BlockRegistrable, BlockWithItem {

		private final AtomicBoolean registered = new AtomicBoolean(false);

		private BlockItem item = null;

		public Stone(Settings settings) {
			this(settings, false);
		}

		public Stone(Settings settings, boolean hasItem) {
			this(settings, hasItem, (ItemGroup) null);
		}

		public Stone(Settings settings, boolean hasItem, ItemGroup itemGroup) {
			this(settings, hasItem, itemGroup != null ? new QuiltItemSettings().group(itemGroup) : new QuiltItemSettings());
		}

		public Stone(Settings settings, boolean hasItem, Item.Settings itemSettings) {
			super(settings);
			if (hasItem) this.item = new BlockItem(this, itemSettings);
		}

		@Override
		public BlockItem getItem() {
			return this.item;
		}

		@Override
		public boolean isNotRegistered() {
			return !this.registered.get();
		}

		@Override
		public void setRegistered() {
			this.registered.set(true);
		}
	}
}
