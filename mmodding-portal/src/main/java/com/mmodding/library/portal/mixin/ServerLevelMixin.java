package com.mmodding.library.portal.mixin;

import com.mmodding.library.portal.api.PortalBinder;
import com.mmodding.library.portal.impl.PortalBinderImpl;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerLevel.class)
public class ServerLevelMixin implements PortalBinder.Container {

	@Unique
	private final PortalBinder portalBinder = new PortalBinderImpl((ServerLevel) (Object) this);

	@Override
	@SuppressWarnings("AddedMixinMembersNamePattern")
	public PortalBinder getPortalBinder() {
		return this.portalBinder;
	}
}
