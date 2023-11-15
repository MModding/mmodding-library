package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.BlockEntityTypeDuckInterface;
import com.mmodding.mmodding_lib.library.blockentities.BlockEntityTypeBlockSupportCallback;
import com.mojang.datafixers.types.Type;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import org.quiltmc.qsl.base.api.event.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin<T extends BlockEntity> implements BlockEntityTypeDuckInterface {

	@Unique
	private final Event<BlockEntityTypeBlockSupportCallback> supportBlocksCallBack = Event.create(BlockEntityTypeBlockSupportCallback.class, callbacks -> supportedBlocks -> {
		for (BlockEntityTypeBlockSupportCallback callback : callbacks) {
			callback.onSupportedBlocks(supportedBlocks);
		}
	});

	@Shadow
	private Set<Block> blocks;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void init(BlockEntityType.BlockEntityFactory<T> factory, Set<Block> blocks, Type<?> type, CallbackInfo ci) {
		Set<Block> previousBlocks = new HashSet<>(this.blocks);
		List<Block> eventBlocks = new ArrayList<>(previousBlocks.stream().toList());
		this.mmodding_lib$supportBlocksCallback().invoker().onSupportedBlocks(eventBlocks);
		previousBlocks.addAll(eventBlocks);
		this.blocks = previousBlocks;
	}

	@Override
	public Event<BlockEntityTypeBlockSupportCallback> mmodding_lib$supportBlocksCallback() {
		return this.supportBlocksCallBack;
	}
}
