package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.PortalForcerDuckInterface;
import com.mmodding.mmodding_lib.library.blocks.CustomSquaredPortalBlock;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.Heightmap;
import net.minecraft.world.PortalForcer;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Comparator;
import java.util.Optional;

@Mixin(PortalForcer.class)
public abstract class PortalForcerMixin implements PortalForcerDuckInterface {

	@Shadow
	@Final
	private ServerWorld world;

	@Shadow
	protected abstract boolean isValidPortalPos(BlockPos pos, BlockPos.Mutable temp, Direction portalDirection, int distanceOrthogonalToPortal);

	@Unique
	boolean useCustomPortalElements;

	@Unique
	Pair<Block, CustomSquaredPortalBlock> customPortalElements;

	@Override
	public void setUseCustomPortalElements(boolean useCustomPortalElements) {
		this.useCustomPortalElements = useCustomPortalElements;
	}

	@Override
	public void setCustomPortalElements(Block frameBlock, CustomSquaredPortalBlock portalBlock) {
		this.customPortalElements = new Pair<>(frameBlock, portalBlock);
	}

	@Unique
	@Override
	public Optional<BlockLocating.Rectangle> searchCustomPortal(RegistryKey<PointOfInterestType> poiKey, BlockPos destPos, WorldBorder worldBorder) {
		PointOfInterestStorage storage = this.world.getPointOfInterestStorage();
		storage.preloadChunks(this.world, destPos, 128);

		Optional<PointOfInterest> optional = storage.getInSquare(
			holder -> holder.isRegistryKey(poiKey),
			destPos,
			128,
			PointOfInterestStorage.OccupationStatus.ANY
		).filter(
			pointOfInterest -> worldBorder.contains(pointOfInterest.getPos())
		).sorted(
			Comparator.comparingDouble(pointOfInterest -> ((PointOfInterest) pointOfInterest).getPos().getSquaredDistance(destPos))
				.thenComparingInt(object -> ((PointOfInterest) object).getPos().getY())
		).filter(pointOfInterest -> this.world.getBlockState(pointOfInterest.getPos()).contains(Properties.HORIZONTAL_AXIS)).findFirst();

		return optional.map(pointOfInterest -> {
			BlockPos blockPos = pointOfInterest.getPos();
			this.world.getChunkManager().addTicket(ChunkTicketType.PORTAL, new ChunkPos(blockPos), 3, blockPos);
			BlockState blockState = this.world.getBlockState(blockPos);

			return BlockLocating.getLargestRectangle(
				blockPos,
				blockState.get(Properties.HORIZONTAL_AXIS),
				21,
				Direction.Axis.Y,
				21,
				pos -> this.world.getBlockState(pos) == blockState
			);
		});
	}

	@Inject(method = "createPortal", at = @At(value = "HEAD"), cancellable = true)
	private void createPortal(BlockPos pos, Direction.Axis axis, CallbackInfoReturnable<Optional<BlockLocating.Rectangle>> cir) {
		if (this.useCustomPortalElements) {

			Direction direction = Direction.get(Direction.AxisDirection.POSITIVE, axis);
			double double0 = -1.0;
			BlockPos firstPos = null;
			double double1 = -1.0;
			BlockPos secondPos = null;
			WorldBorder border = this.world.getWorldBorder();
			int integer0 = Math.min(this.world.getTopY(), this.world.getBottomY() + this.world.m_aqoiocut()) - 1;
			BlockPos.Mutable mutable = pos.mutableCopy();

			for (BlockPos.Mutable actualMutable : BlockPos.iterateInSquare(pos, 16, Direction.EAST, Direction.SOUTH)) {
				int integer1 = Math.min(integer0, this.world.getTopY(Heightmap.Type.MOTION_BLOCKING, actualMutable.getX(), actualMutable.getZ()));

				if (border.contains(actualMutable) && border.contains(actualMutable.move(direction, 1))) {
					actualMutable.move(direction.getOpposite(), 1);

					for (int intIterator0 = integer1; intIterator0 >= this.world.getBottomY(); intIterator0--) {
						actualMutable.setY(intIterator0);

						if (this.world.isAir(actualMutable)) {
							int a = intIterator0;

							while (intIterator0 > this.world.getBottomY() && this.world.isAir(actualMutable.move(Direction.DOWN))) {
								intIterator0--;
							}

							if (intIterator0 + 4 <= integer0) {
								int b = a - intIterator0;

								if (b <= 0 || b >= 3) {
									actualMutable.setY(intIterator0);

									if (this.isValidPortalPos(actualMutable, mutable, direction, 0)) {
										double squaredDistance = pos.getSquaredDistance(actualMutable);

										if (this.isValidPortalPos(actualMutable, mutable, direction, -1) && this.isValidPortalPos(actualMutable, mutable, direction, 1) && (double0 == -1.0 || double0 > squaredDistance)) {
											double0 = squaredDistance;
											firstPos = actualMutable.toImmutable();
										}

										if (double0 == -1.0 && (double1 == -1.0 || double1 > squaredDistance)) {
											double1 = squaredDistance;
											secondPos = actualMutable.toImmutable();
										}
									}
								}
							}
						}
					}
				}
			}

			if (double0 == -1.0 && double1 != -1.0) {
				firstPos = secondPos;
				double0 = double1;
			}

			if (double0 == -1.0) {
				int integer2 = Math.max(this.world.getBottomY() + 1, 70);
				int integer3 = integer0 - 9;
				if (integer3 < integer2) {
					cir.setReturnValue(Optional.empty());
				}

				firstPos = new BlockPos(pos.getX(), MathHelper.clamp(pos.getY(), integer2, integer3), pos.getZ()).toImmutable();
				Direction direction2 = direction.rotateYClockwise();
				if (!border.contains(firstPos)) {
					cir.setReturnValue(Optional.empty());
				}

				for (int k = -1; k < 2; k++) {
					for (int l = 0; l < 2; l++) {
						for (int m = -1; m < 3; m++) {
							BlockState obsidianOrAirState = m < 0 ? this.customPortalElements.getFirst().getDefaultState() : Blocks.AIR.getDefaultState();
							mutable.set(firstPos, l * direction.getOffsetX() + k * direction2.getOffsetX(), m, l * direction.getOffsetZ() + k * direction2.getOffsetZ());
							this.world.setBlockState(mutable, obsidianOrAirState);
						}
					}
				}
			}

			for (int o = -1; o < 3; o++) {
				for (int p = -1; p < 4; p++) {
					if (o == -1 || o == 2 || p == -1 || p == 3) {
						mutable.set(firstPos, o * direction.getOffsetX(), p, o * direction.getOffsetZ());
						this.world.setBlockState(mutable, this.customPortalElements.getFirst().getDefaultState(), Block.NOTIFY_ALL);
					}
				}
			}

			BlockState blockState2 = this.customPortalElements.getSecond().getDefaultState().with(NetherPortalBlock.AXIS, axis);

			for (int p = 0; p < 2; p++) {
				for (int j = 0; j < 3; j++) {
					mutable.set(firstPos, p * direction.getOffsetX(), j, p * direction.getOffsetZ());
					this.world.setBlockState(mutable, blockState2, Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
				}
			}

			cir.setReturnValue(Optional.of(new BlockLocating.Rectangle(firstPos.toImmutable(), 2, 3)));
		}
	}
}
