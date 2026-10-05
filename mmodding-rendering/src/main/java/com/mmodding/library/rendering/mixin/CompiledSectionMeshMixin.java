package com.mmodding.library.rendering.mixin;

import com.mmodding.library.java.api.list.BiList;
import com.mmodding.library.rendering.impl.client.CompiledSectionMeshDuck;
import com.mmodding.library.rendering.impl.client.SectionCompilerResultsDuck;
import net.minecraft.client.renderer.chunk.CompiledSectionMesh;
import net.minecraft.client.renderer.chunk.SectionCompiler;
import net.minecraft.client.renderer.chunk.TranslucencyPointOfView;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CompiledSectionMesh.class)
public class CompiledSectionMeshMixin implements CompiledSectionMeshDuck {

	@Unique
	private BiList<BlockPos, BlockState> specialBlocks;

	// we collect the special blocks for them to be extracted by the level extractor
	@Inject(method = "<init>", at = @At("TAIL"))
	private void collectSpecialBlocks(TranslucencyPointOfView translucencyPointOfView, SectionCompiler.Results results, long compileTaskStartTimeNs, CallbackInfo ci) {
		this.specialBlocks = ((SectionCompilerResultsDuck) (Object) results).mmodding$specialBlocks();
	}

	@Override
	public BiList<BlockPos, BlockState> mmodding$specialBlocks() {
		return this.specialBlocks;
	}
}
