package com.mmodding.library.energy.api.access.catalog;

import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.access.EnergyAccess;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;

/**
 * A sided {@link EnergyAccess}. Takes the side access definitions and a given non-null side to apply.
 * <br>Each {@link SideAccess} can specify an insertion rate and an extraction rate.
 */
public class SidedEnergyAccess implements EnergyAccess {

	private final EnergyAccess delegate;
	private final Map<Direction, SideAccess> definition;
	private final Direction side;

	public SidedEnergyAccess(EnergyAccess delegate, Map<Direction, SideAccess> definition, @NotNull Direction side) {
		this.delegate = delegate;
		this.definition = definition;
		this.side = side;
	}

	@Override
	public boolean supportsInsertion() {
		return this.delegate.supportsInsertion() && Optional.ofNullable(this.definition.get(this.side)).map(sideAccess -> sideAccess.insertionRate).orElse(0L) > 0;
	}

	@Override
	public long insert(long amount, TransactionContext context) {
		long actualAmount = 0;
		if (this.definition.containsKey(this.side)) {
			actualAmount = Math.min(this.definition.get(this.side).insertionRate, amount);
		}
		return this.delegate.insert(actualAmount, context);
	}

	@Override
	public boolean supportsExtraction() {
		return this.delegate.supportsExtraction() && Optional.ofNullable(this.definition.get(this.side)).map(sideAccess -> sideAccess.extractionRate).orElse(0L) > 0;
	}

	@Override
	public long extract(long amount, TransactionContext context) {
		long actualAmount = 0;
		if (this.definition.containsKey(this.side)) {
			actualAmount = Math.min(this.definition.get(this.side).extractionRate, amount);
		}
		return this.delegate.extract(actualAmount, context);
	}

	@Override
	public boolean isEmpty() {
		return this.delegate.isEmpty();
	}

	@Override
	public boolean isFull() {
		return this.delegate.isFull();
	}

	@Override
	public long amount() {
		return this.delegate.amount();
	}

	@Override
	public long remaining() {
		return this.delegate.remaining();
	}

	@Override
	public long capacity() {
		return this.delegate.capacity();
	}

	@Override
	public EnergyUnit unit() {
		return this.delegate.unit();
	}

	public static class SideAccess {

		protected final long insertionRate;
		protected final long extractionRate;

		public static SideAccess inputOnly(long insertionRate) {
			return new SideAccess(insertionRate, 0L);
		}

		public static SideAccess outputOnly(long extractionRate) {
			return new SideAccess(0L, extractionRate);
		}

		public static SideAccess both(long insertionRate, long extractionRate) {
			return new SideAccess(insertionRate, extractionRate);
		}

		private SideAccess(long insertionRate, long extractionRate) {
			this.insertionRate = insertionRate;
			this.extractionRate = extractionRate;
		}
	}
}
