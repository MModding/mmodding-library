package com.mmodding.library.block.mixin;

import com.mmodding.library.core.api.registry.Registrable;
import com.mmodding.library.core.api.registry.RegistrationStatus;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Block.class)
@SuppressWarnings("AddedMixinMembersNamePattern")
public class BlockMixin implements Registrable<Block> {

	@Unique
	private RegistrationStatus status = RegistrationStatus.UNREGISTERED;

	@Override
	public RegistrationStatus getRegistrationStatus() {
		return this.status;
	}

	@Override
	public void postRegister() {
		this.status = RegistrationStatus.REGISTERED;
	}

	@Override
	public Block as() {
		return (Block) (Object) this;
	}
}
