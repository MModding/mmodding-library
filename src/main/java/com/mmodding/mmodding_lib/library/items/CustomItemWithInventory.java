package com.mmodding.mmodding_lib.library.items;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.library.containers.AdvancedInventory;
import com.mmodding.mmodding_lib.library.containers.DefaultContainer;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.*;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
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

    public CustomItemWithInventory(Settings settings, DefaultContainer defaultContainer) {
        super(settings.maxCount(1));
		this.defaultContainer = defaultContainer;
		this.screenHandlerFunc = null;
		this.inventory = new AdvancedInventory(true, defaultContainer.getSize());
		this.inventory.addInventoryOpenedListener(this::inventoryOpened);
		this.inventory.addInventoryClosedListener(this::inventoryClosed);
    }

	public CustomItemWithInventory(Settings settings, int size, TriFunction<Integer, PlayerInventory, Inventory, ScreenHandler> screenHandlerFunc) {
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

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);
		if (stack.getNbt() != null) {
			if (stack.getNbt().contains("Items", NbtElement.LIST_TYPE)) {
				NbtList nbtList = stack.getNbt().getList("Items", NbtElement.COMPOUND_TYPE);
				for (int i = 0; i < nbtList.size(); i++) {
					ItemStack itemStack = ItemStack.fromNbt(nbtList.getCompound(i));
					if (!itemStack.isEmpty()) {
						tooltip.add(itemStack.getName().copy().append(" x").append(String.valueOf(itemStack.getCount())));
					}
				}
			}
		}
	}

	@Nullable
	public SoundEvent getUseSound() {
		return null;
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

	public boolean isOpened() {
		return this.opened;
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
			MModdingLib.mmoddingLib.getLogger().error("ItemStack in Active Player Hand is not an CustomItemWithInventory!");
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

	@Override
    public boolean isNotRegistered() {
        return !this.registered.get();
    }

    @Override
    public void setRegistered() {
		this.registered.set(true);
    }
}
