package com.mmodding.library.portal.test;

import com.mmodding.library.core.api.AdvancedContainer;
import com.mmodding.library.core.api.ExtendedModInitializer;
import com.mmodding.library.core.api.management.ElementsManager;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PoiHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class MModdingPortalTests implements ExtendedModInitializer {

	public static final ResourceKey<PoiType> EXAMPLE_POI = ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, Identifier.fromNamespaceAndPath("mmodding_portal_testmod", "example_poi"));

	public static final Block EXAMPLE_PORTAL_BLOCK = new ExamplePortalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).noCollision().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("mmodding_portal_testmod", "example_portal_block"))));

	@Override
	public void setupManager(ElementsManager manager) {}

	@Override
	public void onInitialize(AdvancedContainer mod) {
		mod.register(BuiltInRegistries.BLOCK, "example_portal_block", EXAMPLE_PORTAL_BLOCK);
		PoiHelper.register(EXAMPLE_POI.identifier(), 0, 1, EXAMPLE_PORTAL_BLOCK);
	}
}
