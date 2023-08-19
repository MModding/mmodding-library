package com.mmodding.mmodding_lib.library.stellar;

import com.mmodding.mmodding_lib.library.stellar.client.StellarCycle;

public class StellarStatus {

    private final long fullRotationTime;

    private long time;

    public StellarStatus(long fullRotationTime) {
        this.fullRotationTime = fullRotationTime;
    }

    public static StellarStatus of(StellarCycle stellarCycle) {
        return new StellarStatus(stellarCycle.getFullRotationTime());
    }

    public void tick() {
        this.time = this.time < this.fullRotationTime ? this.time + 1L : 0L;
    }

    public void setCurrentTime(long time) {
        this.time = time;
    }

    public long getCurrentTime() {
        return this.time;
    }

    public long getFullTime() {
        return this.fullRotationTime;
    }
}
