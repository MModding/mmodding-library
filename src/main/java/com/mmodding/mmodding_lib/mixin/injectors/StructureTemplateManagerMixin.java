package com.mmodding.mmodding_lib.mixin.injectors;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mmodding.mmodding_lib.ducks.StructureDuckInterface;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(StructureTemplateManager.class)
public class StructureTemplateManagerMixin {

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	@ModifyReturnValue(method = "tryLoadStructure", at = @At("RETURN"))
	private Optional<Structure> provideStructureIdentifier(Optional<Structure> original, Identifier structure) {
		if (original.isPresent()) {
			StructureDuckInterface duck = (StructureDuckInterface) original.get();
			duck.mmodding_lib$setIdentifier(structure);
			return Optional.of((Structure) duck);
		}
		else {
			return original;
		}
	}
}
