package com.mmodding.mmodding_lib.library.portals;

import com.mmodding.mmodding_lib.library.portals.squared.CustomSquaredPortal;
import com.mmodding.mmodding_lib.library.portals.squared.UnlinkedCustomSquaredPortal;
import com.mmodding.mmodding_lib.library.utils.Registrable;
import com.mmodding.mmodding_lib.library.utils.RegistrationUtils;
import net.minecraft.util.Identifier;

public interface PortalRegistrable extends Registrable {

	default void register(Identifier identifier) {
		if (this instanceof CustomSquaredPortal squaredPortal && this.isNotRegistered()) {
			RegistrationUtils.registerSquaredPortal(identifier, squaredPortal);
			this.setRegistered();
		}
		else if (this instanceof UnlinkedCustomSquaredPortal squaredPortal && this.isNotRegistered()) {
			RegistrationUtils.registerUnlinkedSquaredPortal(identifier, squaredPortal);
			this.setRegistered();
		}
	}
}
