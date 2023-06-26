package com.mmodding.mmodding_lib.ducks;

import com.mmodding.mmodding_lib.library.blocks.CustomSquaredPortalBlock;
import net.minecraft.block.Block;

public interface PortalForcerDuck {

	void setUseCustomPortalElements(boolean useCustomPortalElements);

	void setCustomPortalElements(Block frameBlock, CustomSquaredPortalBlock portalBlock);
}
