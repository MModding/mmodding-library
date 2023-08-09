package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.glint.GlintPackView;
import com.mmodding.mmodding_lib.interface_injections.ItemGlintPack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ItemGlintPack {

    @Shadow
    public abstract Item getItem();

    @Nullable
    @Override
    public GlintPackView getGlintPackView() {
        return this.getItem().getGlintPackView();
    }
}
