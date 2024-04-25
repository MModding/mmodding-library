package com.mmodding.mmodding_lib.mixin.accessors;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TridentEntity.class)
public interface TridentEntityAccessor {

	@Accessor("LOYALTY")
	static TrackedData<Byte> getLoyalty() {
		throw new IllegalStateException();
	}

	@Accessor("ENCHANTED")
	static TrackedData<Boolean> getEnchanted() {
		throw new IllegalStateException();
	}

    @Accessor
    ItemStack getTridentStack();

	@Accessor("tridentStack")
	void setTridentStack(ItemStack tridentStack);
}
