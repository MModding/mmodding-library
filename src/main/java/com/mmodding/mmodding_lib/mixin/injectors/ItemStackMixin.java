package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.mmodding_lib.ducks.ItemStackDuckInterface;
import com.mmodding.mmodding_lib.interface_injections.ItemGlintPack;
import com.mmodding.mmodding_lib.library.glint.GlintPackView;
import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;
import com.mmodding.mmodding_lib.library.items.stacks.data.HiddenStackDataStorage;
import com.mmodding.mmodding_lib.library.utils.Self;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ConcurrentModificationException;
import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ItemStackDuckInterface, ItemGlintPack, Self<ItemStack> {

	@Unique
	public HiddenStackDataStorage hiddenStorage = new HiddenStackDataStorage();

    @Shadow
    public abstract Item getItem();

	@WrapOperation(method = "getTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/MutableText;formatted(Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/MutableText;", ordinal = 0))
	private MutableText getTooltip(MutableText mutableText, Formatting formatting, Operation<MutableText> original) {
		try {
			List<Formatting> formattings = AdvancedItemSettings.NAME_FORMATTINGS.get(this.getItem());
			if (!formattings.isEmpty()) {
				formattings.forEach(mutableText::formatted);
				return mutableText;
			} else {
				return original.call(mutableText, formatting);
			}
		}
		catch (ConcurrentModificationException concurrentModificationException) {
			return original.call(mutableText, formatting);
		}
	}

	@WrapOperation(method = "toHoverableText", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/MutableText;formatted(Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/MutableText;", ordinal = 1))
	private MutableText toHoverableText(MutableText mutableText, Formatting formatting, Operation<MutableText> original) {
		List<Formatting> formattings = AdvancedItemSettings.NAME_FORMATTINGS.get(this.getItem());
		if (!formattings.isEmpty()) {
			formattings.forEach(mutableText::formatted);
			return mutableText;
		}
		else {
			return original.call(mutableText, formatting);
		}
	}

	@Override
	public HiddenStackDataStorage mmodding_lib$getHiddenStackData() {
		return this.hiddenStorage;
	}

	@Override
	public void mmodding_lib$setHiddenStackData(HiddenStackDataStorage storage) {
		this.hiddenStorage = storage;
	}

	@Nullable
	@Override
	public GlintPackView getGlintPackView() {
		return this.getItem().getGlintPackView();
	}
}
