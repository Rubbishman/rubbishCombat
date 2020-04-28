package com.rubbishman.rubbishcombat.gui;

public class TempData {
    public TweenHelper curHealth;
    public int maxHealth, maxArmour, curArmour, dodgeDamage, dodgePeriod;

    public TempData(int maxHealth, TweenHelper curHealth, int maxArmour, int curArmour, int dodgeDamage, int dodgePeriod) {
        this.maxHealth = maxHealth;
        this.curHealth = curHealth;
        this.maxArmour = maxArmour;
        this.curArmour = curArmour;
        this.dodgeDamage = dodgeDamage;
        this.dodgePeriod = dodgePeriod;
    }
}
