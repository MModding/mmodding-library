package com.mmodding.library.integration.trinkets;

import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.function.BiConsumer;

public class TrinketCallbackImpl implements TrinketCallback {

	private final Item item;

	public TrinketCallbackImpl(Item item) {
		this.item = item;
	}

	@Override
	public void forEachTrinketModifier(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity, Identifier slotIdentifier, BiConsumer<Holder<Attribute>, AttributeModifier> consumer) {
		ItemAttributeModifiers iam = this.item.components().get(DataComponents.ATTRIBUTE_MODIFIERS);
		if (iam != null) {
			for (ItemAttributeModifiers.Entry entry : iam.modifiers()) {
				consumer.accept(entry.attribute(), entry.modifier());
			}
		}
	}
}
