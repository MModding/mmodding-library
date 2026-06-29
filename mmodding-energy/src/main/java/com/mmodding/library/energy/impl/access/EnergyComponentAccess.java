package com.mmodding.library.energy.impl.access;

import com.mmodding.library.energy.api.EnergyComponent;
import com.mmodding.library.energy.api.EnergyUnit;
import com.mmodding.library.energy.api.access.EnergyAccess;
import com.mmodding.library.energy.impl.EnergyComponentImpl;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import org.jetbrains.annotations.NotNull;

public class EnergyComponentAccess extends SnapshotParticipant<@NotNull Long> implements EnergyAccess {

	protected final EnergyComponentImpl component;

	public EnergyComponentAccess(EnergyComponent component) {
		this.component = (EnergyComponentImpl) component;
	}

	@Override
	public boolean isEmpty() {
		return this.component.isEmpty();
	}

	@Override
	public boolean isFull() {
		return this.component.isFull();
	}

	@Override
	public long amount() {
		return this.component.amount();
	}

	@Override
	public long remaining() {
		return this.component.remaining();
	}

	@Override
	public long capacity() {
		return this.component.capacity();
	}

	@Override
	public EnergyUnit unit() {
		return this.component.unit();
	}

	@Override
	public boolean supportsInsertion() {
		return true;
	}

	@Override
	public long insert(long amount, TransactionContext context) {
		updateSnapshots(context);
		long actualAmount = Math.min(this.remaining(), amount);
		this.component.data().setAmount(this.component.data().getAmount() + actualAmount);
		return actualAmount;
	}

	@Override
	public boolean supportsExtraction() {
		return true;
	}

	@Override
	public long extract(long amount, TransactionContext context) {
		updateSnapshots(context);
		long actualAmount = Math.min(this.amount(), amount);
		this.component.data().setAmount(this.component.data().getAmount() - actualAmount);
		return actualAmount;
	}

	@Override
	protected Long createSnapshot() {
		return this.component.data().getAmount();
	}

	@Override
	protected void readSnapshot(Long snapshot) {
		this.component.data().setAmount(snapshot);
	}
}
