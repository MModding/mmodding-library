package com.mmodding.mmodding_lib.library.portals;

import com.mmodding.mmodding_lib.library.items.ItemRegistrable;
import com.mmodding.mmodding_lib.library.utils.MModdingGlobalMaps;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Direction;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomSquaredPortalKeyItem extends Item implements ItemRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

	private final Identifier portalIdentifier;

    public CustomSquaredPortalKeyItem(Settings settings, Identifier portalIdentifier) {
        super(settings);
		this.portalIdentifier = portalIdentifier;
    }

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		Pair<? extends Block, ? extends CustomSquaredPortalBlock> pair = MModdingGlobalMaps.getCustomSquaredPortal(this.portalIdentifier);
		if (pair != null) {
			Optional<CustomSquaredPortalAreaHelper> optional = CustomSquaredPortalAreaHelper.getNewCustomPortal(
				pair.getLeft(), pair.getRight(), context.getWorld(), context.getBlockPos(), Direction.Axis.X
			);
			optional.ifPresent(CustomSquaredPortalAreaHelper::createPortal);
		}
		return super.useOnBlock(context);
	}

	@Override
    public boolean isNotRegistered() {
        return !registered.get();
    }

    @Override
    public void setRegistered() {
        registered.set(true);
    }
}
