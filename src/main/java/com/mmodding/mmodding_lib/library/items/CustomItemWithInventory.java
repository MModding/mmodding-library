package com.mmodding.mmodding_lib.library.items;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.library.containers.AdvancedInventory;
import com.mmodding.mmodding_lib.library.containers.DefaultContainer;
import com.mmodding.mmodding_lib.library.items.tooltipdata.InventoryTooltipData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.screen.*;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class CustomItemWithInventory extends Item implements ItemRegistrable, NamedScreenHandlerFactory {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	private final DefaultContainer defaultContainer;
	private final TriFunction<Integer, PlayerInventory, Inventory, ScreenHandler> screenHandlerFunc;
	private final AdvancedInventory inventory;

	private Hand hand = null;
	private boolean opened = false;

    public CustomItemWithInventory(DefaultContainer defaultContainer, Settings settings) {
        super(settings.maxCount(1));
		this.defaultContainer = defaultContainer;
		this.screenHandlerFunc = null;
		this.inventory = new AdvancedInventory(true, defaultContainer.getSize());
		this.inventory.addInventoryOpenedListener(this::inventoryOpened);
		this.inventory.addInventoryClosedListener(this::inventoryClosed);
    }

	public CustomItemWithInventory(TriFunction<Integer, PlayerInventory, Inventory, ScreenHandler> screenHandlerFunc, int size, Settings settings) {
		super(settings.maxCount(1));
		this.defaultContainer = DefaultContainer.NULL;
		this.screenHandlerFunc = screenHandlerFunc;
		this.inventory = new AdvancedInventory(true, size);
		this.inventory.addInventoryOpenedListener(this::inventoryOpened);
		this.inventory.addInventoryClosedListener(this::inventoryClosed);
	}

	@Override
	public boolean canBeNested() {
		return false;
	}

	@Nullable
	public DefaultedList<ItemStack> getContent(ItemStack stack) {
		if (stack.getNbt() != null) {
			if (stack.getNbt().contains("Items", NbtElement.LIST_TYPE)) {
				DefaultedList<ItemStack> content = DefaultedList.ofSize(this.inventory.size(), ItemStack.EMPTY);
				Inventories.readNbt(stack.getNbt(), content);
				return content;
			}
		}
		return null;
	}

	@Override
	public Optional<TooltipData> getTooltipData(ItemStack stack) {
		if (this.getContent(stack) != null) {
			return this.appendTooltipSlots(this.getContent(stack));
		}
		else {
			return super.getTooltipData(stack);
		}
	}

	public Optional<TooltipData> appendTooltipSlots(DefaultedList<ItemStack> content) {
		if (this.getTooltipMode().isSlotsOverview()) {
			if (this.getTooltipMode().equals(TooltipMode.ALL_SLOTS_OVERVIEW)) {
				if (this.defaultContainer != DefaultContainer.NULL) {
					int rows = switch (this.defaultContainer) {
						case DEFAULT_9X1 -> 1;
						case DEFAULT_9X2 -> 2;
						case DEFAULT_9X4 -> 4;
						case DEFAULT_9X5 -> 5;
						case DEFAULT_9X6 -> 6;
						default -> 3;
					};
					int columns = !this.defaultContainer.equals(DefaultContainer.DEFAULT_3X3) ? 9 : 3;
					return Optional.of(new InventoryTooltipData(this.inventory, content, rows, columns));
				}
			}
			else if (this.getTooltipMode().equals(TooltipMode.FILLED_SLOTS_OVERVIEW)) {
				return Optional.of(new InventoryTooltipData(this.inventory, content, true, false));
			}
			else if (this.getTooltipMode().equals(TooltipMode.GROUPED_SLOTS_OVERVIEW)) {
				return Optional.of(new InventoryTooltipData(this.inventory, content, true, true));
			}
		}
		return Optional.empty();
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		if (this.getContent(stack) != null) {
			this.appendTooltipLines(this.getContent(stack), world, tooltip, context);
		}
	}

	public void appendTooltipLines(DefaultedList<ItemStack> content, @Nullable World world, List<Text> tooltip, TooltipContext context) {
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

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		this.hand = hand;
		ItemStack stack = user.getStackInHand(this.hand);
		if (stack.getNbt() != null) {
			if (stack.getNbt().contains("Items", NbtElement.LIST_TYPE)) {
				this.inventory.readSortedNbtList(stack.getNbt().getList("Items", NbtElement.COMPOUND_TYPE));
			}
		}
		user.openHandledScreen(this);
		Optional.ofNullable(this.getUseSound()).ifPresent(
			useSound -> user.playSound(useSound, 0.8f, 0.8f + user.getRandom().nextFloat() * 0.4f)
		);
		return TypedActionResult.success(user.getStackInHand(this.hand));
	}

	private void inventoryOpened(PlayerEntity player, Inventory inventory) {
		this.opened = true;
	}

	private void inventoryClosed(PlayerEntity player, Inventory inventory) {
		ItemStack stack = player.getStackInHand(this.hand);
		if (stack.getItem() instanceof CustomItemWithInventory) {
			NbtCompound nbt = stack.getOrCreateNbt();
			if (nbt.contains("Items", NbtElement.LIST_TYPE)) nbt.remove("Items");
			nbt.put("Items", this.inventory.toSortedNbtList());
			this.inventory.clear();
		}
		else {
			MModdingLib.LIBRARY_CONTAINER.getLogger().error("ItemStack in Active Player Hand is not an CustomItemWithInventory!");
		}
		this.hand = null;
		this.opened = false;
	}

	@Override
	public Text getDisplayName() {
		return this.getName();
	}

	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return switch (this.defaultContainer) {
			case NULL -> this.screenHandlerFunc.apply(syncId, playerInventory, this.inventory);
			case DEFAULT_3X3 -> new Generic3x3ContainerScreenHandler(syncId, playerInventory, this.inventory);
			case DEFAULT_9X1 -> new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X1, syncId, playerInventory, this.inventory, 1);
			case DEFAULT_9X2 -> new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X2, syncId, playerInventory, this.inventory, 2);
			case DEFAULT_9X3 -> GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, this.inventory);
			case DEFAULT_9X4 -> new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X4, syncId, playerInventory, this.inventory, 4);
			case DEFAULT_9X5 -> new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X5, syncId, playerInventory, this.inventory, 5);
			case DEFAULT_9X6 -> GenericContainerScreenHandler.createGeneric9x6(syncId, playerInventory, this.inventory);
		};
	}

	public TooltipMode getTooltipMode() {
		return TooltipMode.LIMITED_LINES;
	}

	@Nullable
	public SoundEvent getUseSound() {
		return null;
	}

	public boolean isOpened() {
		return this.opened;
	}

	@Override
    public boolean isNotRegistered() {
        return !this.registered.get();
    }

    @Override
    public void setRegistered() {
		this.registered.set(true);
    }

	public enum TooltipMode {

		/**
		 * The item does not render any specific tooltips.
		 */
		WITHOUT_ANY,

		/**
		 * The item renders its tooltip in a series of lines indicating its inventory content.
		 * <br>Number of lines are limited just like shulkers.
		 * @apiNote Default value of {@link CustomItemWithInventory#getTooltipMode()}
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
		 * <br>Overview will follow inventory shape when using {@link DefaultContainer}, otherwise it will not.
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
