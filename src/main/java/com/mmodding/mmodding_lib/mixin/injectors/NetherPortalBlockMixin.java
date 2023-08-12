package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.NetherPortalBlockDuckInterface;
import com.mmodding.mmodding_lib.library.portals.squared.CustomSquaredPortalBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(NetherPortalBlock.class)
public class NetherPortalBlockMixin extends AbstractBlockMixin implements NetherPortalBlockDuckInterface {

	@Inject(method = "randomDisplayTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private void randomDisplayTick(BlockState state, World world, BlockPos pos, RandomGenerator random, CallbackInfo ci, int i, double d, double e, double f, double g, double h, double j) {

		NetherPortalBlock thisObject = (NetherPortalBlock) (Object) this;

		if (thisObject instanceof CustomSquaredPortalBlock portalBlock) {
			world.addParticle(portalBlock.getParticleType(), d, e, f, g, h, j);
		}
	}

	@Unique
	public BlockState mmodding_lib$getAbstractStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
}
