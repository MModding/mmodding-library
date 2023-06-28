package com.mmodding.mmodding_lib.library.blocks;

import com.mmodding.mmodding_lib.ducks.NetherPortalBlockDuckInterface;
import com.mmodding.mmodding_lib.ducks.EntityDuckInterface;
import com.mmodding.mmodding_lib.library.pois.CustomPOI;
import com.mmodding.mmodding_lib.library.portals.CustomPortalAreaHelper;
import com.mmodding.mmodding_lib.library.utils.RegistrationUtils;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.poi.PointOfInterestType;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomSquaredPortalBlock extends NetherPortalBlock implements BlockRegistrable, BlockWithItem {

    private final AtomicBoolean registered = new AtomicBoolean(false);
    private BlockItem item = null;

	private final Block frameBlock;
	private final RegistryKey<World> worldKey;
	private final RegistryKey<PointOfInterestType> poiKey;

    public CustomSquaredPortalBlock(Block frameBlock, Identifier dimensionId, Settings settings) {
        this(frameBlock, dimensionId, settings, false);
    }

    public CustomSquaredPortalBlock(Block frameBlock, Identifier dimensionId, Settings settings, boolean hasItem) {
        this(frameBlock, dimensionId, settings, hasItem, (ItemGroup) null);
    }

	public CustomSquaredPortalBlock(Block frameBlock, Identifier dimensionId, Settings settings, boolean hasItem, ItemGroup itemGroup) {
		this(frameBlock, dimensionId, settings, hasItem, itemGroup != null ? new QuiltItemSettings().group(itemGroup) : new QuiltItemSettings());
	}

    public CustomSquaredPortalBlock(Block frameBlock, Identifier dimensionId, Settings settings, boolean hasItem, Item.Settings itemSettings) {
        super(settings);
        if (hasItem) this.item = new BlockItem(this, itemSettings);
		this.frameBlock = frameBlock;
		this.worldKey = RegistryKey.of(Registry.WORLD_KEY, dimensionId);
		Identifier poiId = new Identifier(dimensionId.getNamespace(), dimensionId.getPath() + "_portal_poi");
		this.poiKey = RegistryKey.of(Registry.POINT_OF_INTEREST_TYPE_KEY, poiId);
		new CustomPOI(this, 0, 1).register(poiId);
    }

	public void registerPortal(Identifier identifier) {
		RegistrationUtils.registerCustomPortal(identifier, this.frameBlock, this);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		Direction.Axis directionAxis = direction.getAxis();
		Direction.Axis stateAxis = state.get(AXIS);
		boolean bl = stateAxis != directionAxis && directionAxis.isHorizontal();
		return !bl && !neighborState.isOf(this) && !new CustomPortalAreaHelper(this.frameBlock, this, world, pos, stateAxis).wasAlreadyValid()
			? Blocks.AIR.getDefaultState()
			: ((NetherPortalBlockDuckInterface) this).getAbstractStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		if (!entity.hasVehicle() && !entity.hasPassengers() && entity.canUsePortals()) {
			((EntityDuckInterface) entity).setInCustomPortal(this.frameBlock, this, pos);
		}
	}

	public RegistryKey<World> getWorldKey() {
		return this.worldKey;
	}

	public RegistryKey<PointOfInterestType> getPoiKey() {
		return this.poiKey;
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
