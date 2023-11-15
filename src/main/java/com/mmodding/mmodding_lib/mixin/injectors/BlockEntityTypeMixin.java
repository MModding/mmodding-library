package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.BlockEntityTypeDuckInterface;
import com.mmodding.mmodding_lib.library.blockentities.BlockEntityTypeBlockSupportCallback;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import org.quiltmc.qsl.base.api.event.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin implements BlockEntityTypeDuckInterface {

	@Unique
	private final Event<BlockEntityTypeBlockSupportCallback> supportBlocksCallBack = Event.create(BlockEntityTypeBlockSupportCallback.class, callbacks -> supportedBlocks -> {
		for (BlockEntityTypeBlockSupportCallback callback : callbacks) {
			callback.onSupportedBlocks(supportedBlocks);
		}
	});

	@Inject(method = "supports", at = @At("HEAD"), cancellable = true)
	private void supports(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		List<Block> supportedBlocks = new ArrayList<>();
		this.mmodding_lib$supportBlocksCallback().invoker().onSupportedBlocks(supportedBlocks);
		if (supportedBlocks.contains(state.getBlock())) {
			cir.setReturnValue(true);
		}
	}

	@Override
	public Event<BlockEntityTypeBlockSupportCallback> mmodding_lib$supportBlocksCallback() {
		return this.supportBlocksCallBack;
	}
}
