package com.mmodding.library.item.impl.wrapper;

import com.mmodding.library.item.api.wrapper.ItemHeap;
import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.java.api.function.Mapper;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ItemHeapImpl implements ItemHeap {

	private final Map<String, Item> items;

	public ItemHeapImpl(NameItemFactory<? extends Item> factory, AutoMapper<String> nameMapper, Mapper<String, Item.Properties> propertiesMapper, String namespace, List<String> constructors) {
		Map<String, Item> items = new Object2ObjectLinkedOpenHashMap<>();
		for (String constructor : constructors) {
			String name = nameMapper.map(constructor);
			Item item = Items.registerItem(
				ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(namespace, name)),
				properties -> factory.make(constructor, properties),
				propertiesMapper.map(constructor)
			);
			items.put(constructor, item);
		}
		this.items = items;
	}

	@Override
	public List<Item> getEntries() {
		return List.copyOf(this.items.values());
	}

	public ItemHeap map(AutoMapper<Item> mapper) {
		this.items.keySet().forEach(name -> this.items.computeIfPresent(name, (ignored, item) -> mapper.map(item)));
		return this;
	}

	public void forEach(Consumer<Item> consumer) {
		this.items.forEach((name, item) -> consumer.accept(item));
	}
}
