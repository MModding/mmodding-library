package com.mmodding.library.item.mixin;

import com.mmodding.library.core.api.registry.Registrable;
import com.mmodding.library.core.api.registry.RegistrationStatus;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Mixin(Item.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public class ItemMixin implements Registrable<Item> {

	@Unique
	private static final Set<Item> BUILTIN_ITEMS = Arrays.stream(Items.class.getDeclaredFields())
		.filter(field -> field.getDeclaringClass().equals(Item.class))
		.map(field -> {
			try {
				return (Item) field.get(Items.class);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		})
		.collect(Collectors.toSet());

	@Unique
	private RegistrationStatus status = RegistrationStatus.UNREGISTERED;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void cancelBuiltinBlocks(Item.Settings settings, CallbackInfo ci) {
		if (ItemMixin.BUILTIN_ITEMS.contains(this.as())) {
			this.status = RegistrationStatus.CANCELLED;
		}
	}

	@Override
	public RegistrationStatus getRegistrationStatus() {
		return this.status;
	}

	@Override
	public void postRegister(Identifier identifier, Item item) {
		this.status = RegistrationStatus.REGISTERED;
	}

	@Override
	public Item as() {
		return (Item) (Object) this;
	}
}
