package com.rubbishman.rubbishcombat.gui.data;

import com.rubbishman.rubbishcombat.gui.helper.TweenHelper;
import com.rubbishman.rubbishcombat.state.CombatEntity;

public class TempData {
    public TweenHelper curHealth, curArmour;
    public long maxHealth, maxArmour, dodgeDamage, armorDamage, armorFatigue;

    public TempData() {}

    public TempData(long maxHealth, TweenHelper curHealth, long maxArmour, TweenHelper curArmour, long dodgeDamage, long armorDamage) {
        this.maxHealth = maxHealth;
        this.curHealth = curHealth;
        this.maxArmour = maxArmour;
        this.curArmour = curArmour;
        this.dodgeDamage = dodgeDamage;
        this.armorDamage = armorDamage;
    }

    public void updateFromCombatEntity(CombatEntity combatEntity) {
        this.maxHealth = combatEntity.maxHealth;
        this.curHealth = new TweenHelper(combatEntity.currentHealth, combatEntity.currentHealth, 0, 40, 0.05);
        this.maxArmour = combatEntity.defense.maxDefense;
        this.curArmour = new TweenHelper(combatEntity.defense.currentDefense, combatEntity.defense.currentDefense, 0, 40, 0.1);
        this.dodgeDamage = combatEntity.dodge.current;
        this.armorDamage = combatEntity.defense.defenseDamage;
        this.armorFatigue = combatEntity.defense.defenseFatigue;
    }
}
