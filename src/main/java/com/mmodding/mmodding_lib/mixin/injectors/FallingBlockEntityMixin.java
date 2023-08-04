package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.FallingBlockEntityDuckInterface;
import com.mmodding.mmodding_lib.library.blocks.interactions.FallingBlockInteraction;
import com.mmodding.mmodding_lib.library.blocks.interactions.data.FallingBlockInteractionData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends EntityMixin implements FallingBlockEntityDuckInterface {

	@Unique
	BlockState initialBlockState;

	@Unique
	float finalFallDistance;

	@Shadow
    private BlockState block;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/FallingBlockEntity;discard()V", shift = At.Shift.AFTER, ordinal = 1), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void tickOnLanding(CallbackInfo ci, Block block, BlockPos blockPos) {
        if (this.getWorld().getBlockState(blockPos.down()).getBlock() instanceof FallingBlockInteraction interaction) {
            interaction.onFallingBlockInteract(FallingBlockInteractionData.of(
                this.getWorld(),
                blockPos,
                this.block,
                this.getWorld().getBlockState(blockPos),
                ((FallingBlockEntity) (Object) this)
            ));
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/FallingBlockEntity;onDestroyedOnLanding(Lnet/minecraft/block/Block;Lnet/minecraft/util/math/BlockPos;)V", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void tickOnDestroyedOnLanding(CallbackInfo ci, Block block, BlockPos blockPos) {
        if (this.getWorld().getBlockState(blockPos.down()).getBlock() instanceof FallingBlockInteraction interaction) {
            interaction.onFallingBlockInteract(FallingBlockInteractionData.of(
                this.getWorld(),
                blockPos,
                this.block,
                this.getWorld().getBlockState(blockPos),
                ((FallingBlockEntity) (Object) this)
            ));
        }
    }

	@Inject(method = "handleFallDamage", at = @At("HEAD"))
	private void handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
		if (fallDistance > 0) {
			this.mmodding_lib$setInitialBlockState(this.block);
			this.mmodding_lib$setFinalFallDistance(fallDistance);
		}
	}

	@Override
	public BlockState mmodding_lib$getInitialBlockState() {
		return this.initialBlockState;
	}

	@Override
	public float mmodding_lib$getFinalFallDistance() {
		return this.finalFallDistance;
	}

	@Override
	public void mmodding_lib$setInitialBlockState(BlockState initialBlockState) {
		this.initialBlockState = initialBlockState;
	}

	@Override
	public void mmodding_lib$setFinalFallDistance(float finalFallDistance) {
		this.finalFallDistance = finalFallDistance;
	}
}
