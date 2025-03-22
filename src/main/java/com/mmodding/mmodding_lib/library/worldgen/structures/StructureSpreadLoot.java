package com.mmodding.mmodding_lib.library.worldgen.structures;

import com.mmodding.mmodding_lib.ducks.LootableContainerBlockEntityDuckInterface;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.ServerWorldAccess;
import org.jetbrains.annotations.ApiStatus;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class StructureSpreadLoot {

	private final Set<ItemStack> structureCommonLoot = new HashSet<>();
	private final Map<Identifier, Set<ItemStack>> structurePieceCommonLoots = new HashMap<>();

	private StructureSpreadLoot() {}

	public static StructureSpreadLoot create() {
		return new StructureSpreadLoot();
	}

	public StructureSpreadLoot addStructureCommonLoot(ItemStack... stacks) {
		this.structureCommonLoot.addAll(Set.of(stacks));
		return this;
	}

	public StructureSpreadLoot addStructurePieceCommonLoot(Identifier structurePiece, ItemStack... stacks) {
		this.structurePieceCommonLoots.compute(structurePiece, ((identifier, itemStacks) -> {
			if (itemStacks == null) {
				return new HashSet<>(Set.of(stacks));
			}
			else {
				itemStacks.addAll(Set.of(stacks));
				return itemStacks;
			}
		}));
		return this;
	}

	@ApiStatus.Internal
	public StructureSpreadLootProvider createProvider() {
		return new StructureSpreadLootProvider(this);
	}

	@ApiStatus.Internal
	public static class StructureSpreadLootProvider {

		private final StructureSpreadLoot structureSpreadLoot;

		private final List<BlockPos> structureContainers;
		private final Map<Identifier, List<BlockPos>> structurePieceContainers;

		private StructureSpreadLootProvider(StructureSpreadLoot structureSpreadLoot) {
			this.structureSpreadLoot = structureSpreadLoot;
			this.structureContainers = new ArrayList<>();
			this.structurePieceContainers = new HashMap<>();
		}

		public Consumer<BlockPos> structureContainersCollector() {
			return this.structureContainers::add;
		}

		public BiConsumer<Identifier, BlockPos> structurePieceContainersCollector() {
			return (structurePiece, pos) -> this.structurePieceContainers.compute(structurePiece, (ignored, blockPos) -> {
				if (blockPos == null) {
					return new ArrayList<>(List.of(pos));
				}
				else {
					blockPos.add(pos);
					return blockPos;
				}
			});
		}

		public void spreadLoots(ServerWorldAccess world, RandomGenerator random) {
			if (!this.structureContainers.isEmpty()) {
				this.structureSpreadLoot.structureCommonLoot.forEach(stack -> {
					BlockPos pos = this.structureContainers.get(random.nextInt(this.structureContainers.size()));
					if (world.getBlockEntity(pos) instanceof LootableContainerBlockEntity lootableContainerBlockEntity) {
						((LootableContainerBlockEntityDuckInterface) lootableContainerBlockEntity).mmodding_lib$addPredeterminedLoot(stack);
					}
				});
				this.structureContainers.clear();
				for (Identifier structurePiece : this.structureSpreadLoot.structurePieceCommonLoots.keySet()) {
					if (this.structurePieceContainers.containsKey(structurePiece)) {
						this.structureSpreadLoot.structurePieceCommonLoots.get(structurePiece).forEach(stack -> {
							List<BlockPos> positions = this.structurePieceContainers.get(structurePiece);
							BlockPos pos = positions.get(random.nextInt(positions.size()));
							if (world.getBlockEntity(pos) instanceof LootableContainerBlockEntity lootableContainerBlockEntity) {
								((LootableContainerBlockEntityDuckInterface) lootableContainerBlockEntity).mmodding_lib$addPredeterminedLoot(stack);
							}
						});
					}
				}
			}
		}
	}
}
