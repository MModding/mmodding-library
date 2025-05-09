package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mmodding.mmodding_lib.interface_injections.HiddenDataStorage;
import com.mmodding.mmodding_lib.interface_injections.ItemGlintPack;
import com.mmodding.mmodding_lib.interface_injections.TagRuntimeManagement;
import com.mmodding.mmodding_lib.library.glint.GlintPackView;
import com.mmodding.mmodding_lib.library.items.settings.AdvancedItemSettings;
import com.mmodding.mmodding_lib.library.network.support.type.NetworkMap;
import com.mmodding.mmodding_lib.library.tags.modifiers.TagModifier;
import com.mmodding.mmodding_lib.library.utils.Self;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.TagKey;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ConcurrentModificationException;
import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements TagRuntimeManagement.ItemStackTagRuntimeManagement, HiddenDataStorage, ItemGlintPack, Self<ItemStack> {

	@Unique
	public NetworkMap hiddenDataStorage = new NetworkMap();

    @Shadow
    public abstract Item getItem();

	@Shadow
	public abstract boolean isIn(TagKey<Item> tag);

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

	@Inject(method = "copy", at = @At("TAIL"))
	private void copy(CallbackInfoReturnable<ItemStack> cir) {
		cir.getReturnValue().setHiddenDataStorage(this.getHiddenDataStorage());
	}

	@Override
	public boolean isIn(TagModifier<Item> modifier) {
		return modifier.result(this.getItem(), this::isIn);
	}

	@Override
	public NetworkMap getHiddenDataStorage() {
		return this.hiddenDataStorage;
	}

	@Override
	public void setHiddenDataStorage(NetworkMap storage) {
		this.hiddenDataStorage = storage;
	}

	@Nullable
	@Override
	public GlintPackView getGlintPackView() {
		return this.getItem().getGlintPackView();
	}
}
