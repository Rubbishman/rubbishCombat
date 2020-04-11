package com.rubbishman.rubbishcombat.state.attribute;

public class DefensiveAttribute {
    public final int currentDefense;
    public final int maxDefense;
    public final float defenseRatio;
    public final int regenPeriod;
    public final int regenFactor;

    public DefensiveAttribute(int currentDefense, int maxDefense, float defenseRatio, int regenPeriod, int regenFactor) {
        this.currentDefense = currentDefense;
        this.maxDefense = maxDefense;
        this.defenseRatio = defenseRatio;
        this.regenPeriod = regenPeriod;
        this.regenFactor = regenFactor;
    }
}
