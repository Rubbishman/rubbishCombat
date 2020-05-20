package com.rubbishman.rubbishcombat.gui.data;

import com.rubbishman.rubbishcombat.gui.helper.TweenHelper;

public class TempData {
    public TweenHelper curHealth, curArmour;
    public long maxHealth, maxArmour, dodgeDamage, armorDamage;

    public TempData(long maxHealth, TweenHelper curHealth, long maxArmour, TweenHelper curArmour, long dodgeDamage, long armorDamage) {
        this.maxHealth = maxHealth;
        this.curHealth = curHealth;
        this.maxArmour = maxArmour;
        this.curArmour = curArmour;
        this.dodgeDamage = dodgeDamage;
        this.armorDamage = armorDamage;
    }
}
