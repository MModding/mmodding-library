package com.mmodding.library.core.api.registry;

import com.mmodding.library.core.api.management.info.InjectedContent;
import com.mmodding.library.core.impl.registry.StaticElementImpl;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

/**
 * An interface representing static elements of the game, such as {@link Block} or {@link Item}.
 * @param <T> the class of the element
 */
@InjectedContent({Block.class, Item.class})
public interface StaticElement<T> extends StaticElementImpl<T> {

	default void register(RegistryKey<T> key) {
		Registry<T> registry = this.mmodding$getRegistry();
		if (!registry.contains(key)) {
			Registry.register(registry, key, this.mmodding$as());
		}
		else {
			throw new IllegalStateException("Tried registering " + key + " in " + registry.getKey() + " but it is already registered");
		}
	}

	/**
	 * @deprecated use {@link StaticElement#register(RegistryKey)} instead
	 */
	@Deprecated
	default void register(Identifier identifier) {
		Registry<T> registry = this.mmodding$getRegistry();
		if (!registry.containsId(identifier)) {
			Registry.register(registry, identifier, this.mmodding$as());
		}
		else {
			throw new IllegalStateException("Tried registering " + identifier + " in " + registry.getKey() + " but it is already registered");
		}
	}
}
