package com.rubbishman.rubbishcombat.gui;

public class TempData {
    public TweenHelper curHealth, curArmour;
    public long maxHealth, maxArmour,dodgeDamage;

    public TempData(long maxHealth, TweenHelper curHealth, long maxArmour, TweenHelper curArmour, long dodgeDamage) {
        this.maxHealth = maxHealth;
        this.curHealth = curHealth;
        this.maxArmour = maxArmour;
        this.curArmour = curArmour;
        this.dodgeDamage = dodgeDamage;
    }
}
