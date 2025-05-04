package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mmodding.mmodding_lib.library.base.AdvancedModContainer;
import com.mmodding.mmodding_lib.library.base.MModdingBootstrapInitializer;
import com.mmodding.mmodding_lib.library.initializers.invokepoints.BootstrapInvokePoint;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.Bootstrap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Consumer;

@Mixin(Bootstrap.class)
public abstract class BootstrapMixin {

	@Inject(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/FireBlock;registerDefaultFlammables()V"))
	private static void initialize(
		CallbackInfo ci,
		@Share("flammables") LocalRef<Consumer<BootstrapInvokePoint.Placement>> flammables,
		@Share("compostableItems") LocalRef<Consumer<BootstrapInvokePoint.Placement>> compostableItems,
		@Share("brewingRecipes") LocalRef<Consumer<BootstrapInvokePoint.Placement>> brewingRecipes,
		@Share("entitySelectorOptions") LocalRef<Consumer<BootstrapInvokePoint.Placement>> entitySelectorOptions,
		@Share("dispenserBehaviors") LocalRef<Consumer<BootstrapInvokePoint.Placement>> dispenserBehaviors,
		@Share("cauldronBehaviors") LocalRef<Consumer<BootstrapInvokePoint.Placement>> cauldronBehaviors,
		@Share("registries") LocalRef<Consumer<BootstrapInvokePoint.Placement>> registries
	) {
		List<EntrypointContainer<MModdingBootstrapInitializer>> initializers = FabricLoader.getInstance().getEntrypointContainers(MModdingBootstrapInitializer.ENTRYPOINT_KEY, MModdingBootstrapInitializer.class);

		for (var initializer: initializers) {
			initializer.getEntrypoint().onInitializeBootstrap(AdvancedModContainer.of(initializer.getProvider()));
		}

		flammables.set(placement -> BootstrapMixin.process(initializers, placement, BootstrapInvokePoint.Type.FLAMMABLES));
		compostableItems.set(placement -> BootstrapMixin.process(initializers, placement, BootstrapInvokePoint.Type.COMPOSTABLE_ITEMS));
		brewingRecipes.set(placement -> BootstrapMixin.process(initializers, placement, BootstrapInvokePoint.Type.BREWING_RECIPES));
		entitySelectorOptions.set(placement -> BootstrapMixin.process(initializers, placement, BootstrapInvokePoint.Type.ENTITY_SELECTOR_OPTIONS));
		dispenserBehaviors.set(placement -> BootstrapMixin.process(initializers, placement, BootstrapInvokePoint.Type.DISPENSER_BEHAVIORS));
		cauldronBehaviors.set(placement -> BootstrapMixin.process(initializers, placement, BootstrapInvokePoint.Type.CAULDRON_BEHAVIORS));
		registries.set(placement -> BootstrapMixin.process(initializers, placement, BootstrapInvokePoint.Type.REGISTRIES));
	}

	@WrapOperation(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/FireBlock;registerDefaultFlammables()V"))
	private static void flammables(Operation<Void> original, @Share("flammables") LocalRef<Consumer<BootstrapInvokePoint.Placement>> flammables) {
		flammables.get().accept(BootstrapInvokePoint.Placement.BEFORE);
		original.call();
		flammables.get().accept(BootstrapInvokePoint.Placement.AFTER);
	}

	@WrapOperation(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/ComposterBlock;registerDefaultCompostableItems()V"))
	private static void compostableItems(Operation<Void> original, @Share("compostableItems") LocalRef<Consumer<BootstrapInvokePoint.Placement>> compostableItems) {
		compostableItems.get().accept(BootstrapInvokePoint.Placement.BEFORE);
		original.call();
		compostableItems.get().accept(BootstrapInvokePoint.Placement.AFTER);
	}

	@WrapOperation(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/BrewingRecipeRegistry;registerDefaults()V"))
	private static void brewingRecipes(Operation<Void> original, @Share("brewingRecipes") LocalRef<Consumer<BootstrapInvokePoint.Placement>> brewingRecipes) {
		brewingRecipes.get().accept(BootstrapInvokePoint.Placement.BEFORE);
		original.call();
		brewingRecipes.get().accept(BootstrapInvokePoint.Placement.AFTER);
	}

	@WrapOperation(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/command/EntitySelectorOptions;register()V"))
	private static void entitySelectorOptions(Operation<Void> original, @Share("entitySelectorOptions") LocalRef<Consumer<BootstrapInvokePoint.Placement>> entitySelectorOptions) {
		entitySelectorOptions.get().accept(BootstrapInvokePoint.Placement.BEFORE);
		original.call();
		entitySelectorOptions.get().accept(BootstrapInvokePoint.Placement.AFTER);
	}

	@WrapOperation(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/dispenser/DispenserBehavior;registerDefaults()V"))
	private static void dispenserBehaviors(Operation<Void> original, @Share("dispenserBehaviors") LocalRef<Consumer<BootstrapInvokePoint.Placement>> dispenserBehaviors) {
		dispenserBehaviors.get().accept(BootstrapInvokePoint.Placement.BEFORE);
		original.call();
		dispenserBehaviors.get().accept(BootstrapInvokePoint.Placement.AFTER);
	}

	@WrapOperation(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/cauldron/CauldronBehavior;registerBehavior()V"))
	private static void cauldronBehaviors(Operation<Void> original, @Share("cauldronBehaviors") LocalRef<Consumer<BootstrapInvokePoint.Placement>> cauldronBehaviors) {
		cauldronBehaviors.get().accept(BootstrapInvokePoint.Placement.BEFORE);
		original.call();
		cauldronBehaviors.get().accept(BootstrapInvokePoint.Placement.AFTER);
	}

	@WrapOperation(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/registry/Registry;freezeBuiltins()V"))
	private static void registries(Operation<Void> original, @Share("registries") LocalRef<Consumer<BootstrapInvokePoint.Placement>> registries) {
		registries.get().accept(BootstrapInvokePoint.Placement.BEFORE);
		original.call();
		registries.get().accept(BootstrapInvokePoint.Placement.AFTER);
	}

	@Unique
	private static void process(List<EntrypointContainer<MModdingBootstrapInitializer>> initializers, BootstrapInvokePoint.Placement placement, BootstrapInvokePoint.Type type) {
		initializers.forEach(initializer -> initializer.getEntrypoint().processElementsInitializers(placement, type));
	}
}
