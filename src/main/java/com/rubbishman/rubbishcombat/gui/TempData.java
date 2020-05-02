package com.rubbishman.rubbishcombat.gui;

public class TempData {
    public TweenHelper curHealth, curArmour, dodgeDamage;
    public int maxHealth, maxArmour, dodgePeriod;

    public TempData(int maxHealth, TweenHelper curHealth, int maxArmour, TweenHelper curArmour, TweenHelper dodgeDamage, int dodgePeriod) {
        this.maxHealth = maxHealth;
        this.curHealth = curHealth;
        this.maxArmour = maxArmour;
        this.curArmour = curArmour;
        this.dodgeDamage = dodgeDamage;
        this.dodgePeriod = dodgePeriod;
    }
}
