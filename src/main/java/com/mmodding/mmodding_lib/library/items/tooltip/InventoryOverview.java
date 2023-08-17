package com.mmodding.mmodding_lib.library.items.tooltip;

import com.mmodding.mmodding_lib.library.containers.DefaultContainer;
import com.mmodding.mmodding_lib.library.items.tooltip.data.InventoryTooltipData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface InventoryOverview {

	Inventory getInventory();

	default DefaultContainer getDefaultContainer() {
		return DefaultContainer.NULL;
	}

	@Nullable
	default DefaultedList<ItemStack> getContent(ItemStack stack) {
		if (stack.getNbt() != null) {
			if (stack.getNbt().contains("Items", NbtElement.LIST_TYPE)) {
				DefaultedList<ItemStack> content = DefaultedList.ofSize(this.getInventory().size(), ItemStack.EMPTY);
				Inventories.readNbt(stack.getNbt(), content);
				return content;
			}
		}
		return null;
	}

	default Optional<TooltipData> appendTooltipSlots(DefaultedList<ItemStack> content) {
		if (this.getTooltipMode().isSlotsOverview()) {
			if (this.getTooltipMode().equals(TooltipMode.ALL_SLOTS_OVERVIEW)) {
				if (this.getOptionalColumns().isPresent() && this.getOptionalRows().isPresent()) {
					return Optional.of(new InventoryTooltipData(this.getInventory(), content, this.getOptionalColumns().getAsInt(), this.getOptionalRows().getAsInt()));
				}
				else if (this.getDefaultContainer() != DefaultContainer.NULL) {
					int rows = switch (this.getDefaultContainer()) {
						case DEFAULT_9X1 -> 1;
						case DEFAULT_9X2 -> 2;
						case DEFAULT_9X4 -> 4;
						case DEFAULT_9X5 -> 5;
						case DEFAULT_9X6 -> 6;
						default -> 3;
					};
					int columns = !this.getDefaultContainer().equals(DefaultContainer.DEFAULT_3X3) ? 9 : 3;
					return Optional.of(new InventoryTooltipData(this.getInventory(), content, rows, columns));
				}
				else {
					return Optional.of(new InventoryTooltipData(this.getInventory(), content, false, false));
				}
			}
			else if (this.getTooltipMode().equals(TooltipMode.FILLED_SLOTS_OVERVIEW)) {
				return Optional.of(new InventoryTooltipData(this.getInventory(), content, true, false));
			}
			else if (this.getTooltipMode().equals(TooltipMode.GROUPED_SLOTS_OVERVIEW)) {
				return Optional.of(new InventoryTooltipData(this.getInventory(), content, true, true));
			}
		}
		return Optional.empty();
	}

	default void appendTooltipLines(DefaultedList<ItemStack> content, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		if (this.getTooltipMode().isLines()) {
			int total = 0;
			int displayed = 0;

			for (ItemStack stack : content) {
				if (!stack.isEmpty()) {
					total++;
					if (this.getTooltipMode().equals(TooltipMode.UNLIMITED_LINES) || displayed <= 4) {
						displayed++;
						tooltip.add(stack.getName().copy().append(" x").append(String.valueOf(stack.getCount())));
					}
				}
			}

			if (this.getTooltipMode().equals(TooltipMode.LIMITED_LINES) && total - displayed > 0) {
				tooltip.add(Text.translatable("container.shulkerBox.more", total - displayed).formatted(Formatting.ITALIC));
			}
		}
	}

	default TooltipMode getTooltipMode() {
		return TooltipMode.LIMITED_LINES;
	}

	default OptionalInt getOptionalColumns() {
		return OptionalInt.empty();
	}

	default OptionalInt getOptionalRows() {
		return OptionalInt.empty();
	}

	enum TooltipMode {

		/**
		 * The item does not render any specific tooltips.
		 */
		WITHOUT_ANY,

		/**
		 * The item renders its tooltip in a series of lines indicating its inventory content.
		 * <br>Number of lines are limited just like shulkers.
		 * @apiNote Default value of {@link InventoryOverview#getTooltipMode()}
		 */
		LIMITED_LINES,

		/**
		 * The item renders its tooltip in a series of lines indicating its inventory content.
		 * <br>Number of lines are unlimited.
		 */
		UNLIMITED_LINES,

		/**
		 * The item renders its tooltip in a series of slots indicating its inventory content.
		 * <br>Slots are rendered for each type of item regardless of their number just like bundles.
		 * <br>Overview does not follow inventory shape.
		 */
		GROUPED_SLOTS_OVERVIEW,

		/**
		 * The item renders its tooltip in a series of slots indicating its inventory content.
		 * <br>Only filled slots are rendered.
		 * <br>Overview does not follow inventory shape.
		 */
		FILLED_SLOTS_OVERVIEW,

		/**
		 * The item renders its tooltip in a series of slots indicating its inventory content.
		 * <br>All slots are rendered even if there are empty.
		 * <br>Overview will follow an inventory shape when using {@link InventoryOverview#getOptionalColumns()}
		 * and {@link InventoryOverview#getOptionalRows()} or {@link DefaultContainer}, otherwise it will not.
		 */
		ALL_SLOTS_OVERVIEW;

		public boolean isNothing() {
			return this.equals(WITHOUT_ANY);
		}

		public boolean isLines() {
			return this.equals(LIMITED_LINES) || this.equals(UNLIMITED_LINES);
		}

		public boolean isSlotsOverview() {
			return this.equals(GROUPED_SLOTS_OVERVIEW) || this.equals(FILLED_SLOTS_OVERVIEW) || this.equals(ALL_SLOTS_OVERVIEW);
		}
	}
}
