package com.mmodding.mmodding_lib.library.client.tooltip;

import com.mmodding.mmodding_lib.library.items.tooltip.data.InventoryTooltipData;
import net.minecraft.client.gui.tooltip.BundleTooltipComponent;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class InventoryTooltipComponent extends BundleTooltipComponent {

    private final InventoryTooltipData data;

    public InventoryTooltipComponent(InventoryTooltipData data) {
        super(data.toBundleTooltipData());
        this.data = data;
    }

    public InventoryTooltipData getData() {
        return this.data;
    }
}
