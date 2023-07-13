package com.mmodding.mmodding_lib.library.items;

import com.mmodding.mmodding_lib.MModdingLib;
import com.mmodding.mmodding_lib.library.containers.AdvancedInventory;
import com.mmodding.mmodding_lib.library.containers.DefaultContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.screen.*;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class CustomItemWithInventory extends Item implements ItemRegistrable, NamedScreenHandlerFactory {

	private final AtomicBoolean registered = new AtomicBoolean(false);

	private final DefaultContainer defaultContainer;
	private final TriFunction<Integer, PlayerInventory, Inventory, ScreenHandler> screenHandlerFunc;
	private final AdvancedInventory inventory;

    public CustomItemWithInventory(Settings settings, DefaultContainer defaultContainer) {
        super(settings);
		this.defaultContainer = defaultContainer;
		this.screenHandlerFunc = null;
		this.inventory = new AdvancedInventory(defaultContainer.getSize());
		this.inventory.addInventoryClosedListener(this::inventoryClosed);
    }

	public CustomItemWithInventory(Settings settings, int size, TriFunction<Integer, PlayerInventory, Inventory, ScreenHandler> screenHandlerFunc) {
		super(settings);
		this.defaultContainer = DefaultContainer.NULL;
		this.screenHandlerFunc = screenHandlerFunc;
		this.inventory = new AdvancedInventory(size);
		this.inventory.addInventoryClosedListener(this::inventoryClosed);
	}

	@Override
	public void postProcessNbt(NbtCompound nbt) {
		super.postProcessNbt(nbt);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		if (stack.getNbt() != null) {
			if (stack.getNbt().contains("Items", NbtElement.LIST_TYPE)) {
				this.inventory.readNbtList(stack.getNbt().getList("Items", NbtElement.COMPOUND_TYPE));
			}
		}
		user.openHandledScreen(this);
		return TypedActionResult.success(user.getStackInHand(hand));
	}

	private void inventoryClosed(PlayerEntity player, Inventory inventory) {
		Hand hand = player.getActiveHand();
		ItemStack stack = player.getStackInHand(hand);
		if (stack.getItem() instanceof CustomItemWithInventory) {
			NbtCompound nbt = stack.getOrCreateNbt();
			nbt.put("Items", this.inventory.toNbtList());
			this.inventory.clear();
		}
		else {
			MModdingLib.mmoddingLib.getLogger().error("ItemStack in Active Player Hand is not an CustomItemWithInventory!");
		}
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
        return !registered.get();
    }

    @Override
    public void setRegistered() {
        registered.set(true);
    }
}
