package com.mmodding.library.item.api.wrapper;

import com.mmodding.library.item.api.util.ItemFactory;
import com.mmodding.library.item.impl.wrapper.ItemHeapImpl;
import com.mmodding.library.java.api.function.AutoMapper;
import com.mmodding.library.java.api.function.Mapper;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A simple class allowing to create and manipulate a bunch of items at the same time.
 */
public interface ItemHeap {

	/**
	 * Creates a new {@link ItemHeap} from an item factory, shared properties and names.
	 * @param factory the item factory
	 * @param properties the item properties
	 * @param namespace the namespace
	 * @param names the names
	 * @return the item heap
	 * @param <T> the item class type
	 */
	static <T extends Item> ItemHeap register(ItemFactory<T> factory, Supplier<Item.Properties> properties, String namespace, String... names) {
		return ItemHeap.register(factory, name -> name, name -> properties.get(), namespace, names);
	}

	/**
	 * Creates a new {@link ItemHeap} from an item factory, shared properties and names.
	 * @param factory the item factory
	 * @param properties the item properties
	 * @param namespace the namespace
	 * @param names the names
	 * @return the item heap
	 * @param <T> the item class type
	 */
	static <T extends Item> ItemHeap register(ItemFactory<T> factory, Supplier<Item.Properties> properties, String namespace, List<String> names) {
		return new ItemHeapImpl(NameItemFactory.of(factory), name -> name, name -> properties.get(), namespace, names);
	}

	/**
	 * Creates a new {@link ItemHeap} from an item factory, a name mapper, a properties mapper, and string constructors.
	 * @param factory the item factory
	 * @param nameMapper the name mapper
	 * @param propertiesMapper the properties mapper
	 * @param namespace the namespace
	 * @param constructors the string constructors
	 * @return the item heap
	 * @param <T> the item class type
	 */
	static <T extends Item> ItemHeap register(ItemFactory<T> factory, AutoMapper<String> nameMapper, Mapper<String, Item.Properties> propertiesMapper, String namespace, String... constructors)  {
		return ItemHeap.register(NameItemFactory.of(factory), nameMapper, propertiesMapper, namespace, constructors);
	}

	/**
	 * Creates a new {@link ItemHeap} from an item factory, a name mapper, a properties mapper, and names.
	 * @param factory the item factory
	 * @param nameMapper the name mapper
	 * @param propertiesMapper the properties mapper
	 * @param namespace the namespace
	 * @param constructors the string constructors
	 * @return the item heap
	 * @param <T> the item class type
	 */
	static <T extends Item> ItemHeap register(ItemFactory<T> factory, AutoMapper<String> nameMapper, Mapper<String, Item.Properties> propertiesMapper, String namespace, List<String> constructors)  {
		return ItemHeap.register(NameItemFactory.of(factory), nameMapper, propertiesMapper, namespace, constructors);
	}

	/**
	 * Creates a new {@link ItemHeap} from a name item factory, a name mapper, a properties mapper, and names.
	 * @param factory the name item factory
	 * @param nameMapper the name mapper
	 * @param propertiesMapper the properties mapper
	 * @param namespace the namespace
	 * @param constructors the string constructors
	 * @return the item heap
	 * @param <T> the item class type
	 */
	static <T extends Item> ItemHeap register(NameItemFactory<T> factory, AutoMapper<String> nameMapper, Mapper<String, Item.Properties> propertiesMapper, String namespace, String... constructors) {
		return ItemHeap.register(factory, nameMapper, propertiesMapper, namespace, List.of(constructors));
	}

	/**
	 * Creates a new {@link ItemHeap} from a name item factory, a name mapper, a properties mapper, and names.
	 * @param factory the name item factory
	 * @param nameMapper the name mapper
	 * @param propertiesMapper the properties mapper
	 * @param namespace the namespace
	 * @param constructors the string constructors
	 * @return the item heap
	 * @param <T> the item class type
	 */
	static <T extends Item> ItemHeap register(NameItemFactory<T> factory, AutoMapper<String> nameMapper, Mapper<String, Item.Properties> propertiesMapper, String namespace, List<String> constructors) {
		return new ItemHeapImpl(factory, nameMapper, propertiesMapper, namespace, constructors);
	}

	List<Item> getEntries();

	ItemHeap map(AutoMapper<Item> mapper);

	void forEach(Consumer<Item> consumer);

	interface NameItemFactory<T extends Item> {

		static <T extends Item> NameItemFactory<T> of(ItemFactory<T> factory) {
			return (_, properties) -> factory.make(properties);
		}

		T make(String constructor, Item.Properties properties);
	}
}
