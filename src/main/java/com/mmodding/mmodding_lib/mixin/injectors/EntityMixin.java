package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.EntityDuckInterface;
import com.mmodding.mmodding_lib.library.blocks.CustomSquaredPortalBlock;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.AreaHelper;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityDuckInterface {

	@Unique
	boolean inCustomPortal;

	@Unique
	boolean useCustomPortalElements;

	@Unique
	Pair<Block, CustomSquaredPortalBlock> customPortalElements;

	@Unique
	BlockPos lastCustomPortalPosition;

	@Shadow
	public abstract double squaredDistanceTo(Entity entity);

	@Shadow
	public abstract World getWorld();

	@Shadow
	public abstract boolean hasNetherPortalCooldown();

	@Shadow
	public abstract void resetNetherPortalCooldown();

	@Shadow
	public abstract int getMaxNetherPortalTime();

	@Shadow
	public abstract boolean hasVehicle();

	@Shadow
	protected int netherPortalTime;

	@Shadow
	public abstract @Nullable Entity moveToWorld(ServerWorld destination);

	@Shadow
	protected abstract void tickNetherPortalCooldown();

	@Shadow
	public abstract double getX();

	@Shadow
	public abstract double getY();

	@Shadow
	public abstract double getZ();

	@Shadow
	protected abstract Optional<BlockLocating.Rectangle> getPortalRect(ServerWorld destWorld, BlockPos destPos, boolean destIsNether, WorldBorder worldBorder);

	@Shadow
	protected abstract Vec3d positionInPortal(Direction.Axis portalAxis, BlockLocating.Rectangle portalRect);

	@Shadow
	public abstract EntityDimensions getDimensions(EntityPose pose);

	@Shadow
	public abstract EntityPose getPose();

	@Shadow
	public abstract Vec3d getVelocity();

	@Shadow
	public abstract float getYaw();

	@Shadow
	public abstract float getPitch();

	@Inject(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;tickNetherPortal()V", shift = At.Shift.AFTER))
	private void baseTickAfterTickNetherPortal(CallbackInfo ci) {
		this.tickCustomPortal();
	}

	@Inject(method = "getTeleportTarget", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;getRegistryKey()Lnet/minecraft/util/registry/RegistryKey;", shift = At.Shift.AFTER, ordinal = 2), cancellable = true)
	private void getTeleportTarget(ServerWorld destination, CallbackInfoReturnable<TeleportTarget> cir) {
		boolean overworldToNether = this.getWorld().getRegistryKey() != World.OVERWORLD && destination.getRegistryKey() != World.NETHER;
		boolean netherToOverworld = this.getWorld().getRegistryKey() != World.NETHER && destination.getRegistryKey() != World.OVERWORLD;
		if (!overworldToNether && !netherToOverworld) {

			WorldBorder worldBorder = destination.getWorldBorder();
			double coordScaleFactor = DimensionType.getCoordinateScaleFactor(this.getWorld().getDimension(), destination.getDimension());
			BlockPos blockPos2 = worldBorder.m_kgymprsy(this.getX() * coordScaleFactor, this.getY(), this.getZ() * coordScaleFactor);
			this.useCustomPortalElements = true;

			cir.setReturnValue(this.getPortalRect(destination, blockPos2, false, worldBorder).map(rectangle -> {
				BlockState lastCustomPortalState = this.getWorld().getBlockState(this.lastCustomPortalPosition);
				Direction.Axis axisTarget;
				Vec3d vec3dTarget;

				if (lastCustomPortalState.contains(Properties.HORIZONTAL_AXIS)) {
					axisTarget = lastCustomPortalState.get(Properties.HORIZONTAL_AXIS);
					BlockLocating.Rectangle rectangle2 = BlockLocating.getLargestRectangle(
						this.lastCustomPortalPosition,
						axisTarget,
						21,
						Direction.Axis.Y,
						21,
						pos -> this.getWorld().getBlockState(pos) == lastCustomPortalState
					);
					vec3dTarget = this.positionInPortal(axisTarget, rectangle2);
				} else {
					axisTarget = Direction.Axis.X;
					vec3dTarget = new Vec3d(0.5, 0.0, 0.0);
				}

				return AreaHelper.getNetherTeleportTarget(
					destination, rectangle, axisTarget, vec3dTarget, this.getDimensions(this.getPose()), this.getVelocity(), this.getYaw(), this.getPitch()
				);
			}).orElse(null));
		}
	}

	@Unique
	public void setInCustomPortal(Block frameBlock, CustomSquaredPortalBlock portalBlock, BlockPos pos) {
		if (this.hasNetherPortalCooldown()) {
			this.resetNetherPortalCooldown();
		}
		else {
			if (!this.getWorld().isClient() && !pos.equals(this.lastCustomPortalPosition)) {
				this.lastCustomPortalPosition = pos.toImmutable();
			}

			this.inCustomPortal = true;
			this.customPortalElements = new Pair<>(frameBlock, portalBlock);
		}
	}

	@Unique
	public void tickCustomPortal() {
		if (this.getWorld() instanceof ServerWorld serverWorld) {
			int i = this.getMaxNetherPortalTime();
			if (this.inCustomPortal) {
				MinecraftServer minecraftServer = serverWorld.getServer();
				RegistryKey<World> registryKey = this.customPortalElements.getSecond().getWorldKey();
				ServerWorld destinationWorld = minecraftServer.getWorld(registryKey);
				if (destinationWorld != null && minecraftServer.isNetherAllowed() && !this.hasVehicle() && this.netherPortalTime++ >= i) {
					this.getWorld().getProfiler().push("customPortal");
					this.netherPortalTime = i;
					this.resetNetherPortalCooldown();
					this.moveToWorld(destinationWorld);
					this.getWorld().getProfiler().pop();
				}

				this.inCustomPortal = false;
			} else {
				if (this.netherPortalTime > 0) {
					this.netherPortalTime -= 4;
				}

				if (this.netherPortalTime < 0) {
					this.netherPortalTime = 0;
				}
			}

			this.tickNetherPortalCooldown();
		}
	}
}
