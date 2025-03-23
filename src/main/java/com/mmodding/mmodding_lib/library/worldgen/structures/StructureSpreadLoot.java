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

		// It is required to use this method before calling StructureSpreadLoot#spreadLoots.
		// Since I cannot find any place in the Minecraft code that gets executed just after the full generation
		// of a specific structure (in all the chunks that the structure takes), I need to execute
		// StructureSpreadLoot#spreadLoots after the placement of every StructureStart, which creates duplicates
		// of the provided loot. To prevent that, I need to erase the previously spread loot. So yeah it does a lot
		// of stuff for nothing, but I cannot find a better way to achieve that at the moment.
		// It is only an issue about the world generation that is managed for each chunk, as you cannot know from the
		// current chunk if the structure is fully generated or not. It also means that, in example, the PlaceCommand
		// will work properly as it manages all chunks of the structure in the same place.
		public void erasePreviousLoots(ServerWorldAccess world) {
			if (!this.structureContainers.isEmpty()) {
				this.structureContainers.forEach(pos -> {
					if (world.getBlockEntity(pos) instanceof LootableContainerBlockEntity lootableContainerBlockEntity) {
						((LootableContainerBlockEntityDuckInterface) lootableContainerBlockEntity).mmodding_lib$clearPredeterminedLoot();
					}
				});
				for (Identifier structurePiece : this.structurePieceContainers.keySet()) {
					this.structurePieceContainers.get(structurePiece).forEach(pos -> {
						if (world.getBlockEntity(pos) instanceof LootableContainerBlockEntity lootableContainerBlockEntity) {
							((LootableContainerBlockEntityDuckInterface) lootableContainerBlockEntity).mmodding_lib$clearPredeterminedLoot();
						}
					});
				}
			}
		}

		public void spreadLoots(ServerWorldAccess world, RandomGenerator random) {
			if (!this.structureContainers.isEmpty()) {
				this.structureSpreadLoot.structureCommonLoot.forEach(stack -> {
					BlockPos pos = this.structureContainers.get(random.nextInt(this.structureContainers.size()));
					if (world.getBlockEntity(pos) instanceof LootableContainerBlockEntity lootableContainerBlockEntity) {
						((LootableContainerBlockEntityDuckInterface) lootableContainerBlockEntity).mmodding_lib$addPredeterminedLoot(stack);
					}
				});
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
