package com.mmodding.library.energy.api.access;

import com.mmodding.library.energy.api.EnergyUnit;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

import java.util.ArrayList;
import java.util.List;

/**
 * A special-case implementation of {@link EnergyAccess}, delegating to multiple other energy accesses.
 * <br>You will need to implement the behavior of the storage collection with the two following methods:
 * <ul>
 *     <li>{@link EnergyAccess#insert(TransactionContext, long)}</li>
 *     <li>{@link EnergyAccess#extract(TransactionContext, long)}</li>
 * </ul>
 * <br>An application of this is storage redirection to multiple other energy accesses.
 * <br><br>A good example would be energy cables: you collect energy accesses linked through the cables,
 * and make this the energy storage of the cable, and using it would spread to other accesses.
 * @apiNote Collection order is preserved.
 */
public abstract class CompilingEnergyAccess implements EnergyAccess {

	private final EnergyUnit unit;
	protected final List<EnergyAccess> collection;
	protected long totalCapacity;

	protected CompilingEnergyAccess(EnergyUnit unit) {
		this.unit = unit;
		this.collection = new ArrayList<>();
		this.totalCapacity = 0L;
	}

	/**
	 * Pushes another access into this storage collection.
	 * @param access the energy access
	 */
	public final void push(EnergyAccess access) {
		this.collection.add(access);
		this.totalCapacity += EnergyUnit.convert(access.capacity(), access.unit(), this.unit);
	}

	@Override
	public boolean isEmpty() {
		return this.collection.stream().allMatch(EnergyAccess::isEmpty);
	}

	@Override
	public boolean isFull() {
		return this.collection.stream().allMatch(EnergyAccess::isFull);
	}

	@Override
	public long amount() {
		return this.collection.stream()
			.map(storage -> EnergyUnit.convert(storage.amount(), storage.unit(), this.unit))
			.reduce(0L, Long::sum);
	}

	@Override
	public long capacity() {
		return this.totalCapacity;
	}

	@Override
	public EnergyUnit unit() {
		return this.unit;
	}
}
