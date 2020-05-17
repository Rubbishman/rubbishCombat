package com.rubbishman.rubbishcombat.state.attribute;

public class DefensiveAttribute {
    public final long currentDefense;
    public final long maxDefense;
    public final long defenseFactor;
    public final long regenPeriod;
    public final long regenFactor;
    public final double regenRounding;
    public final long damagePeriod;
    public final long defenseDamage;

    public DefensiveAttribute(long currentDefense, long maxDefense, long defenseFactor, long regenPeriod, long regenFactor, long damagePeriod, long defenseDamage) {
        this.currentDefense = currentDefense;
        this.maxDefense = maxDefense;
        this.defenseFactor = defenseFactor;
        this.regenPeriod = regenPeriod;
        this.regenFactor = regenFactor;
        this.damagePeriod = damagePeriod;
        this.defenseDamage = defenseDamage;
        this.regenRounding = 0;
    }

    public DefensiveAttribute(long currentDefense, long maxDefense, long defenseFactor, long regenPeriod, long regenFactor, double regenRounding, long damagePeriod, long defenseDamage) {
        this.currentDefense = currentDefense;
        this.maxDefense = maxDefense;
        this.defenseFactor = defenseFactor;
        this.regenPeriod = regenPeriod;
        this.regenFactor = regenFactor;
        this.regenRounding = regenRounding;
        this.damagePeriod = damagePeriod;
        this.defenseDamage = defenseDamage;
    }
}
