package com.rubbishman.rubbishcombat.state;

import com.rubbishman.rubbishcombat.state.attribute.DefensiveAttribute;

public class CombatEntity {
    public final int currentHealth;
    public final int maxHealth;
    public final DefensiveAttribute defense;

    public CombatEntity(int currentHealth, int maxHealth, DefensiveAttribute defense) {
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
        this.defense = defense;
    }
}
