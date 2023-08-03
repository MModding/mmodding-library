package com.mmodding.mmodding_lib.library.portals;

import com.mmodding.mmodding_lib.library.items.ItemRegistrable;
import com.mmodding.mmodding_lib.library.utils.MModdingGlobalMaps;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomSquaredPortalKeyItem extends Item implements ItemRegistrable {

    private final AtomicBoolean registered = new AtomicBoolean(false);

	private final Identifier portalIdentifier;
	private final SoundEvent soundEvent;

    public CustomSquaredPortalKeyItem(Settings settings, Identifier portalIdentifier, SoundEvent soundEvent) {
        super(settings);
		this.portalIdentifier = portalIdentifier;
		this.soundEvent = soundEvent;
    }

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		BlockPos pos = context.getBlockPos().offset(context.getSide());
		Pair<? extends Block, ? extends CustomSquaredPortalBlock> pair = MModdingGlobalMaps.getCustomSquaredPortal(this.portalIdentifier);
		if (pair != null) {
			Optional<CustomSquaredPortalAreaHelper> optional = CustomSquaredPortalAreaHelper.getNewCustomPortal(
				pair.getLeft(), pair.getRight(), context.getWorld(), pos, Direction.Axis.X
			);
			if (optional.isPresent()) {
				optional.get().createPortal();
				context.getWorld().playSound(null, pos, this.soundEvent, SoundCategory.BLOCKS, 1.0f, context.getWorld().getRandom().nextFloat() * 0.4f + 0.8f);
				return ActionResult.SUCCESS;
			}
		}
		return super.useOnBlock(context);
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
