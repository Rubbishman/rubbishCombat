package com.rubbishman.rubbishcombat.state;

import com.rubbishman.rubbishcombat.state.attribute.DefensiveAttribute;
import com.rubbishman.rubbishcombat.state.attribute.DodgeAttribute;

public class CombatEntity {
    public final int currentHealth;
    public final int maxHealth;
    public final DefensiveAttribute defense;
    public final DodgeAttribute dodge;

    public CombatEntity(int currentHealth, int maxHealth, DefensiveAttribute defense, DodgeAttribute dodge) {
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
        this.defense = defense;
        this.dodge = dodge;
    }
}
