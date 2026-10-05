package com.mmodding.library.rendering.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mmodding.library.java.api.list.BiList;
import com.mmodding.library.rendering.impl.client.SectionCompilerResultsDuck;
import com.mmodding.library.rendering.impl.client.block.SpecialBlockRegistryImpl;
import com.mojang.blaze3d.vertex.VertexSorting;
import net.minecraft.client.renderer.SectionBufferBuilderPack;
import net.minecraft.client.renderer.chunk.RenderSectionRegion;
import net.minecraft.client.renderer.chunk.SectionCompiler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SectionCompiler.class)
public class SectionCompilerMixin {

	// we filter and collect the special blocks at the same time as vanilla does for block entities
	// then it gets collected into the compiled section mesh
	@Inject(method = "compile", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getFluidState()Lnet/minecraft/world/level/material/FluidState;"))
	private void compileSpecialBlocks(SectionPos sectionPos, RenderSectionRegion region, VertexSorting vertexSorting, SectionBufferBuilderPack builders, CallbackInfoReturnable<SectionCompiler.Results> cir, @Local(name = "results") SectionCompiler.Results results, @Local(name = "pos") BlockPos pos, @Local(name = "blockState") BlockState blockState) {
		if (SpecialBlockRegistryImpl.isRegistered(blockState.getBlock())) {
			((SectionCompilerResultsDuck) (Object) results).mmodding$specialBlocks().add(pos.immutable(), blockState);
		}
	}

	@Mixin(SectionCompiler.Results.class)
	private static class ResultsMixin implements SectionCompilerResultsDuck {

		@Unique
		private final BiList<BlockPos, BlockState> specialBlocks = BiList.create();

		@Override
		public BiList<BlockPos, BlockState> mmodding$specialBlocks() {
			return this.specialBlocks;
		}
	}
}
