package com.rubbishman.rubbishcombat.state.attribute;

public class LimitedAttribute {
    public final String name;
    public final int current;
    public final int maximum;
    public final int factor;
    public final int period;
    public final int periodFactor;

    public LimitedAttribute(String name, int current, int maximum, int factor, int period, int periodFactor) {
        this.name = name;
        this.current = current;
        this.maximum = maximum;
        this.factor = factor;
        this.period = period;
        this.periodFactor = periodFactor;
    }
}
