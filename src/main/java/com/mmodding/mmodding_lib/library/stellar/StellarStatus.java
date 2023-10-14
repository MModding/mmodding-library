package com.mmodding.mmodding_lib.library.stellar;

import com.mmodding.mmodding_lib.library.stellar.client.StellarCycle;

public class StellarStatus {

    protected final long fullRotationTime;

    protected long time;

    public StellarStatus(long fullRotationTime) {
        this.fullRotationTime = fullRotationTime;
    }

    public static StellarStatus of(StellarCycle stellarCycle) {
        return new StellarStatus(stellarCycle.getFullRotationTime());
    }

	public static StellarStatus of(long currentTime, long fullTime) {
		StellarStatus stellarStatus = new StellarStatus(fullTime);
		stellarStatus.time = currentTime;
		return stellarStatus;
	}

    public void tick() {
        this.time = this.time < this.fullRotationTime ? this.time + 1L : 0L;
	}

    public long getCurrentTime() {
        return this.time;
    }

    public long getFullTime() {
        return this.fullRotationTime;
    }
}
