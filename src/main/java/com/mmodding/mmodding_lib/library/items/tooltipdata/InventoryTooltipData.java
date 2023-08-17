package com.mmodding.mmodding_lib.library.items.tooltipdata;

import com.mmodding.mmodding_lib.library.inventories.BasicInventory;
import com.mmodding.mmodding_lib.library.utils.InventoryUtils;
import net.minecraft.client.item.BundleTooltipData;
import net.minecraft.client.item.TooltipData;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

import java.util.OptionalInt;

public class InventoryTooltipData implements TooltipData {

    private final Inventory inventory;
    private final DefaultedList<ItemStack> content;
    private final boolean shaped;
    private final int rows;
    private final int columns;
    private final boolean excludeEmptyItems;
    private final boolean groupItems;

    public InventoryTooltipData(SimpleInventory inventory, boolean excludeEmptyItems, boolean groupItems) {
        this(inventory, inventory.stacks, excludeEmptyItems, groupItems);
    }

    public InventoryTooltipData(BasicInventory inventory, boolean excludeEmptyItems, boolean groupItems) {
        this(inventory, inventory.getContent(), excludeEmptyItems, groupItems);
    }

    public InventoryTooltipData(SimpleInventory inventory, int rows, int columns) {
        this(inventory, inventory.stacks, rows, columns);
    }

    public InventoryTooltipData(BasicInventory inventory, int rows, int columns) {
        this(inventory, inventory.getContent(), rows, columns);
    }

    public InventoryTooltipData(Inventory inventory, DefaultedList<ItemStack> content, boolean excludeEmptyItems, boolean groupItems) {
        this.inventory = inventory;
        this.content = content;
        this.shaped = false;
        this.rows = 0;
        this.columns = 0;
        this.excludeEmptyItems = excludeEmptyItems;
        this.groupItems = excludeEmptyItems && groupItems;
    }

    public InventoryTooltipData(Inventory inventory, DefaultedList<ItemStack> content, int rows, int columns) {
        this.inventory = inventory;
        this.content = content;
        this.shaped = true;
        this.rows = rows;
        this.columns = columns;
        this.excludeEmptyItems = false;
        this.groupItems = false;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public DefaultedList<ItemStack> getContent() {
        return this.content;
    }

    public boolean isShaped() {
        return this.shaped;
    }

    public boolean isExcludingEmptyItems() {
        return this.excludeEmptyItems;
    }

    public boolean isGroupingItems() {
        return this.groupItems;
    }

    public OptionalInt getRows() {
        return this.shaped ? OptionalInt.of(this.rows) : OptionalInt.empty();
    }

    public OptionalInt getColumns() {
        return this.shaped ? OptionalInt.of(this.columns) : OptionalInt.empty();
    }

    public BundleTooltipData toBundleTooltipData() {
        if (this.excludeEmptyItems) {
            DefaultedList<ItemStack> content;
            if (this.groupItems) {
                content = InventoryUtils.sort(this.content);
            }
            else {
                content = DefaultedList.of();
                this.content.stream().filter(stack -> !stack.isEmpty()).forEach(content::add);
            }
            BasicInventory inventory = BasicInventory.of(content);
            return new BundleTooltipData(inventory.getContent(), this.inventory.getMaxCountPerStack() * this.inventory.size());
        }
        else {
            return new BundleTooltipData(this.content, this.inventory.getMaxCountPerStack() * this.inventory.size());
        }
    }
}
