package com.rubbishman.rubbishcombat.state.attribute;

public class DodgeAttribute {
    public final long current;
    public final long factor;
    public final long period;
    public final long periodFactor;

    public DodgeAttribute(long current, long factor, long period, long periodFactor) {
        this.current = current;
        this.factor = factor;
        this.period = period;
        this.periodFactor = periodFactor;
    }
}
