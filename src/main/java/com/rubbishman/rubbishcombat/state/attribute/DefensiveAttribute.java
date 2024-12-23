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
    public final long defenseFatigue;
    public final long defenseFatigueRegen;
    public final long defenseFatigueRegenPeriod;
    public final double defenseFatigueRegenRounding;

    public DefensiveAttribute(long currentDefense, long maxDefense, long defenseFactor, long regenPeriod, long regenFactor, long damagePeriod, long defenseDamage, long defenseFatigue, long defenseFatigueRegen, long defenseFatigueRegenPeriod, double regenRounding, double defenseFatigueRegenRounding) {
        this.currentDefense = currentDefense;
        this.maxDefense = maxDefense;
        this.defenseFactor = defenseFactor;
        this.regenPeriod = regenPeriod;
        this.regenFactor = regenFactor;
        this.damagePeriod = damagePeriod;
        this.defenseDamage = defenseDamage;
        this.defenseFatigue = defenseFatigue;
        this.defenseFatigueRegen = defenseFatigueRegen;
        this.defenseFatigueRegenPeriod = defenseFatigueRegenPeriod;
        this.regenRounding = regenRounding;
        this.defenseFatigueRegenRounding = defenseFatigueRegenRounding;
    }
}
