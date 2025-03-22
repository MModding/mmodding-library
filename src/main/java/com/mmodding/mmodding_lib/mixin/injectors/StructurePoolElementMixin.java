package com.mmodding.mmodding_lib.mixin.injectors;

import com.mmodding.mmodding_lib.ducks.StructurePoolElementDuckInterface;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Mixin(StructurePoolElement.class)
public class StructurePoolElementMixin implements StructurePoolElementDuckInterface {

	@Unique
	protected Consumer<BlockPos> structureContainersCollector = null;

	@Unique
	protected BiConsumer<Identifier, BlockPos> structurePieceContainersCollector = null;
}
