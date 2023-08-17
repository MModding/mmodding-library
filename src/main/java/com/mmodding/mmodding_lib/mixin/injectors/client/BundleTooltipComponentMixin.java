package com.mmodding.mmodding_lib.mixin.injectors.client;

import com.mmodding.mmodding_lib.library.client.tooltip.InventoryTooltipComponent;
import net.minecraft.client.gui.tooltip.BundleTooltipComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BundleTooltipComponent.class)
public class BundleTooltipComponentMixin {

    @Inject(method = "getRows", at = @At("HEAD"), cancellable = true)
    private void getRows(CallbackInfoReturnable<Integer> cir) {
        BundleTooltipComponent thisObject = (BundleTooltipComponent) (Object) this;
        if (thisObject instanceof InventoryTooltipComponent component) {
            if (component.getData().getRows().isPresent()) {
                cir.setReturnValue(component.getData().getRows().getAsInt());
            }
        }
    }

    @Inject(method = "getColumns", at = @At("HEAD"), cancellable = true)
    private void getColumns(CallbackInfoReturnable<Integer> cir) {
        BundleTooltipComponent thisObject = (BundleTooltipComponent) (Object) this;
        if (thisObject instanceof InventoryTooltipComponent component) {
            if (component.getData().getColumns().isPresent()) {
                cir.setReturnValue(component.getData().getColumns().getAsInt());
            }
        }
    }
}
