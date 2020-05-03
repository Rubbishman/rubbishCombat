package com.rubbishman.rubbishcombat.state.attribute;

public class DodgeAttribute {
    public final int current;
    public final int factor;
    public final int period;
    public final int periodFactor;

    public DodgeAttribute(int current, int factor, int period, int periodFactor) {
        this.current = current;
        this.factor = factor;
        this.period = period;
        this.periodFactor = periodFactor;
    }
}
