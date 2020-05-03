package com.rubbishman.rubbishcombat.helper;

import com.rubbishman.rubbishcombat.state.CombatEntity;
import com.rubbishman.rubbishcombat.state.attribute.DefensiveAttribute;
import com.rubbishman.rubbishcombat.state.attribute.DodgeAttribute;

public class CombatEntityHelper {
    public static CombatEntity takeArmorDamage(CombatEntity combatEntity, int damageTaken) {
        return new CombatEntity(
                combatEntity.currentHealth,
                combatEntity.maxHealth,
                new DefensiveAttribute(
                        Math.max(combatEntity.defense.currentDefense - damageTaken, 0),
                        combatEntity.defense.maxDefense,
                        combatEntity.defense.defenseFactor,
                        combatEntity.defense.regenPeriod,
                        combatEntity.defense.regenFactor
                ),
                combatEntity.dodge);
    }

    public static CombatEntity takeDodgeDamage(CombatEntity combatEntity, int damageTaken) {
        return new CombatEntity(
                combatEntity.currentHealth,
                combatEntity.maxHealth,
                combatEntity.defense,
                new DodgeAttribute(
                        combatEntity.dodge.current + damageTaken,
                        combatEntity.dodge.factor,
                        combatEntity.dodge.period,
                        combatEntity.dodge.periodFactor
                ));
    }

    public static CombatEntity takeDirectDamage(CombatEntity combatEntity, int damageTaken) {
        return new CombatEntity(
                Math.max(combatEntity.currentHealth - damageTaken, 0),
                combatEntity.maxHealth,
                combatEntity.defense,
                combatEntity.dodge);
    }
}
