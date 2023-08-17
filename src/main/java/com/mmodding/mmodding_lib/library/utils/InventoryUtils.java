package com.mmodding.mmodding_lib.library.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class InventoryUtils {

    public static DefaultedList<ItemStack> sort(DefaultedList<ItemStack> content) {
        Map<Pair<Item, NbtCompound>, Integer> items = new HashMap<>();

        content.stream().filter(stack -> !stack.isEmpty()).forEach(stack -> {
            Pair<Item, NbtCompound> item = Pair.of(stack.getItem(), stack.getNbt());
            int count = items.getOrDefault(item, 0);
            items.put(item, count + stack.getCount());
        });

        DefaultedList<ItemStack> sorted = DefaultedList.of();

        items.forEach((item, count) -> {
            int maxCount = item.getLeft().getMaxCount();

            if (count > maxCount) {
                int remaining = count;

                do {
                    int stackCount = remaining - maxCount <= 0 ? remaining : maxCount;
                    ItemStack stack = new ItemStack(item.getLeft(), stackCount);
                    stack.setNbt(item.getRight());
                    sorted.add(stack);
                    remaining -= maxCount;
                }
                while (remaining > 0);
            }
            else {
                ItemStack stack = new ItemStack(item.getLeft(), count);
                stack.setNbt(item.getRight());
                sorted.add(stack);
            }
        });

        return sorted;
    }
}
