package com.mmodding.library.block.mixin;

import com.mmodding.library.block.api.properties.CustomBlockProperty;
import com.mmodding.library.block.api.properties.MModdingBlockProperties;
import com.mmodding.library.block.impl.properties.CustomBlockPropertyImpl;
import com.mmodding.library.core.api.registry.LiteRegistry;
import com.mmodding.library.java.api.list.BiList;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.Properties.class)
public class BlockBehaviorPropertiesMixin implements MModdingBlockProperties {

	@Shadow
	private @Nullable ResourceKey<Block> id;

	@Unique
	private final BiList<Identifier, Object> customBlockPropertiesQueue = BiList.create();

	@Inject(method = "ofLegacyCopy", at = @At("TAIL"))
	private static void copyCustomProperties(BlockBehaviour behavior, CallbackInfoReturnable<BlockBehaviour.Properties> cir) {
		BlockBehaviour.Properties properties = cir.getReturnValue();
		if (behavior instanceof Block block && CustomBlockPropertyImpl.REGISTRY.hasCompanion(block)) {
			BiList<Identifier, Object> queue = ((BlockBehaviorPropertiesMixin) (Object) properties).customBlockPropertiesQueue;
			LiteRegistry<Object> companion = CustomBlockPropertyImpl.REGISTRY.getCompanion(block);
			for (LiteRegistry.Entry<Object> entry : companion) {
				queue.add(entry.identifier(), entry.element());
			}
		}
	}

	@Inject(method = "setId", at = @At("TAIL"))
	private void dequeueAll(ResourceKey<Block> id, CallbackInfoReturnable<BlockBehaviour.Properties> cir) {
		this.customBlockPropertiesQueue.forEach((property, value) ->
			CustomBlockPropertyImpl.REGISTRY.getOrCreateCompanion(this.id).register(property, value)
		);
		this.customBlockPropertiesQueue.clear();
	}

	@Override
	@SuppressWarnings("AddedMixinMembersNamePattern")
	public <T> BlockBehaviour.Properties custom(CustomBlockProperty<T> property, T value) {
		if (this.id == null) {
			this.customBlockPropertiesQueue.add(property.getIdentifier(), value);
		}
		else {
			CustomBlockPropertyImpl.REGISTRY.getOrCreateCompanion(this.id).register(property.getIdentifier(), value);
		}
		return (BlockBehaviour.Properties) (Object) this;
	}
}
