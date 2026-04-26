package com.mmodding.library.integration.trinkets.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mmodding.library.integration.trinkets.TrinketCallbackImpl;
import com.mmodding.library.item.api.properties.CustomItemProperty;
import com.mmodding.library.item.api.properties.MModdingItemProperties;
import dev.yumi.mc.core.api.ModContainer;
import eu.pb4.trinkets.api.TrinketsApi;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import eu.pb4.trinkets.impl.TrinketsMain;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(TrinketsMain.class)
public class TrinketsMainMixin {

	@Inject(method = "onInitialize", at = @At(value = "INVOKE", target = "Leu/pb4/trinkets/api/TrinketsApi;registerTrinketPredicate(Lnet/minecraft/resources/Identifier;Leu/pb4/trinkets/api/TrinketsApi$TrinketPredicate;)V", ordinal = 2))
	private void injectIntegrationLogic(ModContainer modContainer, CallbackInfo ci, @Share("collectedSlots") LocalRef<Map<Item, List<String>>> collectedSlots) {
		collectedSlots.set(new Object2ObjectOpenHashMap<>());
		for (Item item : BuiltInRegistries.ITEM) {
			List<String> slots = CustomItemProperty.get(item, MModdingItemProperties.TRINKET_SLOTS);
			if (slots != null) {
				TrinketCallback.setCallback(item, new TrinketCallbackImpl(item));
				collectedSlots.get().put(item, slots);
			}
		}
	}

	@WrapOperation(method = "onInitialize", at = @At(value = "INVOKE", target = "Leu/pb4/trinkets/api/TrinketsApi;registerTrinketPredicate(Lnet/minecraft/resources/Identifier;Leu/pb4/trinkets/api/TrinketsApi$TrinketPredicate;)V", ordinal = 2))
	private void injectToDefault(Identifier id, TrinketsApi.TrinketPredicate predicate, Operation<Void> original, @Share("collectedSlots") LocalRef<Map<Item, List<String>>> collectedSlots) {
		TrinketsApi.TrinketPredicate additional = (stack, slot, _) -> collectedSlots.get().containsKey(stack.getItem())
			&& collectedSlots.get().get(stack.getItem()).contains(slot.reference().slot());
		original.call(id, (TrinketsApi.TrinketPredicate) (stack, slot, entity) -> predicate.test(stack, slot, entity) || additional.test(stack, slot, entity));
	}
}
