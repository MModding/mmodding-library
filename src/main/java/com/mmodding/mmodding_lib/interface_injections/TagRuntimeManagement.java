package com.mmodding.mmodding_lib.interface_injections;

import com.mmodding.mmodding_lib.library.tags.modifiers.TagModifier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Holder;
import org.quiltmc.qsl.base.api.util.InjectedInterface;

@InjectedInterface({Holder.class, AbstractBlock.AbstractBlockState.class, FluidState.class, ItemStack.class})
public interface TagRuntimeManagement<T> {

	default boolean isIn(TagModifier<T> modifier) {
		throw new AssertionError();
	}

	interface BlockStateTagRuntimeManagement extends TagRuntimeManagement<Block> {

		/**
		 * BlockState version, avoiding unchecked cast warn.
		 */
		@Override
		default boolean isIn(TagModifier<Block> modifier) {
			throw new AssertionError();
		}
	}

	interface FluidStateTagRuntimeManagement extends TagRuntimeManagement<Fluid> {

		/**
		 * FluidState version, avoiding unchecked cast warn.
		 */
		@Override
		default boolean isIn(TagModifier<Fluid> modifier) {
			throw new AssertionError();
		}
	}

	interface ItemStackTagRuntimeManagement extends TagRuntimeManagement<Item> {

		/**
		 * ItemStack version, avoiding unchecked cast warn.
		 */
		@Override
		default boolean isIn(TagModifier<Item> modifier) {
			throw new AssertionError();
		}
	}
}
