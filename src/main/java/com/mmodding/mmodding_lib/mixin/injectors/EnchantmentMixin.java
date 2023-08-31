package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.enchantments.CustomEnchantment;
import com.mmodding.mmodding_lib.library.enchantments.types.EnchantmentType;
import com.mmodding.mmodding_lib.library.utils.Self;
import net.minecraft.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin implements Self<Enchantment> {

    @Inject(method = "canCombine", at = @At("HEAD"), cancellable = true)
    private void canCombine(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
        EnchantmentType thisType = this.getObject() instanceof CustomEnchantment enchantment ? enchantment.getType() : EnchantmentType.DEFAULT;
        EnchantmentType otherType = other instanceof CustomEnchantment enchantment ? enchantment.getType() : EnchantmentType.DEFAULT;
        if (thisType != otherType) {
            if (!thisType.typeCompatibilities().check(otherType) || !otherType.typeCompatibilities().check(thisType)) {
                cir.setReturnValue(false);
            }
        }
    }
}
