package com.mmodding.library.block.mixin;

import com.mmodding.library.block.api.properties.CustomBlockProperty;
import com.mmodding.library.block.api.properties.MModdingBlockProperties;
import com.mmodding.library.block.impl.properties.CustomBlockPropertyImpl;
import com.mmodding.library.core.api.registry.LiteRegistry;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.Properties.class)
public class BlockBehaviorPropertiesMixin implements MModdingBlockProperties {

	@Inject(method = "ofLegacyCopy", at = @At("TAIL"))
	private static void copyCustomProperties(BlockBehaviour behavior, CallbackInfoReturnable<BlockBehaviour.Properties> cir) {
		BlockBehaviour.Properties properties = cir.getReturnValue();
		if (CustomBlockPropertyImpl.PROPERTIES_COMPANION.hasCompanion(behavior.properties())) {
			LiteRegistry<Object> otherCompanion = CustomBlockPropertyImpl.PROPERTIES_COMPANION.getCompanion(behavior.properties());
			LiteRegistry<Object> companion = CustomBlockPropertyImpl.PROPERTIES_COMPANION.getCompanion(properties);
			for (LiteRegistry.Entry<Object> entry : otherCompanion) {
				companion.register(entry.identifier(), entry.element());
			}
		}
	}

	@Override
	@SuppressWarnings("AddedMixinMembersNamePattern")
	public <T> BlockBehaviour.Properties custom(CustomBlockProperty<T> property, T value) {
		CustomBlockPropertyImpl.PROPERTIES_COMPANION.getOrCreateCompanion((BlockBehaviour.Properties) (Object) this).register(property.getIdentifier(), value);
		return (BlockBehaviour.Properties) (Object) this;
	}
}
