package com.mmodding.mmodding_lib.library.initializers.invokepoints;

public class BootstrapInvokePoint {

    private final Placement placement;
    private final Type type;

    private BootstrapInvokePoint(Placement placement, Type type) {
        this.placement = placement;
        this.type = type;
    }

    public static BootstrapInvokePoint before(Type type) {
        return new BootstrapInvokePoint(Placement.BEFORE, type);
    }

    public static BootstrapInvokePoint after(Type type) {
        return new BootstrapInvokePoint(Placement.AFTER, type);
    }

    public Placement getPlacement() {
        return this.placement;
    }

    public Type getType() {
        return this.type;
    }

    public enum Placement {
        AFTER,
        BEFORE
    }

    public enum Type {
        FLAMMABLES,
        COMPOSTABLE_ITEMS,
        BREWING_RECIPES,
        ENTITY_SELECTOR_OPTIONS,
        DISPENSER_BEHAVIORS,
        CAULDRON_BEHAVIORS,
        REGISTRIES
    }
}
