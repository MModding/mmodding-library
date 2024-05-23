package com.mmodding.library.block.entity.mixin;

import com.mmodding.library.block.entity.api.BlockEntityTypeSupport;
import com.mmodding.library.block.entity.impl.duck.BlockEntityTypeDuck;
import com.mmodding.library.core.api.MModdingLibrary;
import dev.yumi.commons.event.Event;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin implements BlockEntityTypeDuck {

	@Unique
	private final Event<Identifier, BlockEntityTypeSupport> supportBlocksCallBack = MModdingLibrary.getEventManager().create(BlockEntityTypeSupport.class);

	@Inject(method = "supports", at = @At("HEAD"), cancellable = true)
	private void supports(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		List<Block> supportedBlocks = new ArrayList<>();
		this.mmodding$supportCallback().invoker().mutateSupportedBlocks(supportedBlocks);
		if (supportedBlocks.contains(state.getBlock())) {
			cir.setReturnValue(true);
		}
	}

	@Override
	public Event<Identifier, BlockEntityTypeSupport> mmodding$supportCallback() {
		return this.supportBlocksCallBack;
	}
}
