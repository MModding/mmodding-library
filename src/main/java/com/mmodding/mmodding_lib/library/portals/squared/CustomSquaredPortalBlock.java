package com.mmodding.mmodding_lib.library.portals.squared;

import com.mmodding.mmodding_lib.ducks.NetherPortalBlockDuckInterface;
import com.mmodding.mmodding_lib.ducks.EntityDuckInterface;
import com.mmodding.mmodding_lib.library.blocks.BlockRegistrable;
import com.mmodding.mmodding_lib.library.blocks.BlockWithItem;
import com.mmodding.mmodding_lib.library.portals.CustomPortalBlock;
import com.mmodding.mmodding_lib.library.utils.MModdingGlobalMaps;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomSquaredPortalBlock extends NetherPortalBlock implements CustomPortalBlock, BlockRegistrable, BlockWithItem {

    private final AtomicBoolean registered = new AtomicBoolean(false);
    private BlockItem item = null;

	private final DefaultParticleType particleType;

    public CustomSquaredPortalBlock(DefaultParticleType particleType, Settings settings) {
        this(particleType, settings, false);
    }

    public CustomSquaredPortalBlock(DefaultParticleType particleType, Settings settings, boolean hasItem) {
        this(particleType, settings, hasItem, (ItemGroup) null);
    }

	public CustomSquaredPortalBlock(DefaultParticleType particleType, Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(particleType, settings, hasItem, itemGroup != null ? new QuiltItemSettings().group(itemGroup) : new QuiltItemSettings());
	}

    public CustomSquaredPortalBlock(DefaultParticleType particleType, Settings settings, boolean hasItem, Item.Settings itemSettings) {
        super(settings);
        if (hasItem) this.item = new BlockItem(this, itemSettings);
		this.particleType = particleType;
    }

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		for (Identifier identifier : MModdingGlobalMaps.getAllCustomSquaredPortalKeys()) {
			AbstractSquaredPortal squaredPortal = MModdingGlobalMaps.getAbstractSquaredPortal(identifier);

			if (squaredPortal.getPortalBlock() == this) {

				Direction.Axis directionAxis = direction.getAxis();
				Direction.Axis stateAxis = state.get(AXIS);

				boolean valid = stateAxis != directionAxis && directionAxis.isHorizontal();

				if (!valid && !neighborState.isOf(this) && !squaredPortal.wasAlreadyValid(world, pos, stateAxis)) {
					return Blocks.AIR.getDefaultState();
				}
			}
		}
		return ((NetherPortalBlockDuckInterface) this).mmodding_lib$getAbstractStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (!entity.hasVehicle() && !entity.hasPassengers() && entity.canUsePortals()) {
			for (Identifier identifier : MModdingGlobalMaps.getCustomSquaredPortalKeys()) {
				CustomSquaredPortal squaredPortal = MModdingGlobalMaps.getCustomSquaredPortal(identifier);

				if (squaredPortal.getPortalBlock() == this) {
					((EntityDuckInterface) entity).mmodding_lib$setInCustomPortal(squaredPortal, world, pos);
				}
			}
		}
	}

	public DefaultParticleType getParticleType() {
		return this.particleType;
	}

	@Override
	public Block getBlock() {
		return this;
	}

	@Override
    public BlockItem getItem() {
        return this.item;
    }

    @Override
    public boolean isNotRegistered() {
        return !this.registered.get();
    }

    @Override
    public void setRegistered() {
        this.registered.set(true);
    }
}
