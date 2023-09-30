package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.LivingEntityDuckInterface;
import com.mmodding.mmodding_lib.library.entities.data.MModdingTrackedDataHandlers;
import com.mmodding.mmodding_lib.library.entities.data.syncable.SyncableData;
import com.mmodding.mmodding_lib.library.utils.MModdingIdentifier;
import com.mmodding.mmodding_lib.library.utils.ObjectUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin implements LivingEntityDuckInterface {

	@Unique
	private final SyncableData<List<Identifier>> stuckArrowTypes = new SyncableData<>(
		new ArrayList<>(),
		(LivingEntity) (Object) this,
		new MModdingIdentifier("stuck_arrow_types"),
		MModdingTrackedDataHandlers.IDENTIFIER_LIST
	);

	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setStuckArrowCount(I)V"))
	private void tick(CallbackInfo ci) {
		this.deleteStuckArrowType();
	}

	@Override
	public List<Identifier> mmodding_lib$getStuckArrowTypes() {
		return new ArrayList<>(this.stuckArrowTypes.get());
	}

	@Override
	public void mmodding_lib$setStuckArrowTypes(List<Identifier> stuckArrowTypes) {
		this.stuckArrowTypes.set(stuckArrowTypes);
		this.stuckArrowTypes.synchronize();
	}

	@Override
	public void mmodding_lib$addStuckArrowType(Identifier arrowEntityId) {
		this.stuckArrowTypes.get().add(arrowEntityId);
		this.stuckArrowTypes.synchronize();
	}

	@Unique
	private void deleteStuckArrowType() {
		List<Identifier> stuckArrowTypes = this.mmodding_lib$getStuckArrowTypes();
		stuckArrowTypes.remove(stuckArrowTypes.size() - 1);
		this.mmodding_lib$setStuckArrowTypes(stuckArrowTypes);
	}

	static {
		ObjectUtils.load(MModdingTrackedDataHandlers.IDENTIFIER_LIST);
	}
}
