package com.rubbishman.rubbishcombat.state.attribute;

public class DefensiveAttribute {
    public final int currentDefense;
    public final int maxDefense;
    public final int defenseFactor;
    public final int regenPeriod;
    public final int regenFactor;

    public DefensiveAttribute(int currentDefense, int maxDefense, int defenseFactor, int regenPeriod, int regenFactor) {
        this.currentDefense = currentDefense;
        this.maxDefense = maxDefense;
        this.defenseFactor = defenseFactor;
        this.regenPeriod = regenPeriod;
        this.regenFactor = regenFactor;
    }
}
