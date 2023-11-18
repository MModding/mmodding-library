package com.mmodding.mmodding_lib.library.glint;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DynamicGlintPackView implements GlintPackView {

	private final Function<ItemStack, Identifier> function;

	public DynamicGlintPackView(Function<ItemStack, Identifier> function) {
		this.function = function;
	}

	public static DynamicGlintPackView deviate(SimpleGlintPackView view, BiConsumer<ItemStack, AtomicReference<Identifier>> consumer) {
		BiFunction<ItemStack, AtomicReference<Identifier>, AtomicReference<Identifier>> tweak = (stack, ref) -> {
			consumer.accept(stack, ref);
			return ref;
		};
		Function<ItemStack, Identifier> function = stack -> tweak.apply(stack, new AtomicReference<>(view.getIdentifier(stack))).get();
		return new DynamicGlintPackView(function);
	}

	@Override
	public Identifier getIdentifier(ItemStack stack) {
		return this.function.apply(stack);
	}
}
