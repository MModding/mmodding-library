package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.library.blocks.CustomCraftingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingScreenHandler.class)
public class CraftingScreenHandlerMixin {

    @Shadow
    @Final
    private ScreenHandlerContext context;

    @Shadow
    @Final
    private PlayerEntity player;

    @Unique
    private boolean check(World world, BlockPos pos, PlayerEntity player) {
        if (world.getBlockState(pos).getBlock() instanceof CustomCraftingTableBlock) {
            return player.squaredDistanceTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 64.0;
        }
        else {
            return false;
        }
    }

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void canUse(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (this.context.get((world, pos) -> this.check(world, pos, this.player), false)) {
            cir.setReturnValue(true);
        }
    }
}
