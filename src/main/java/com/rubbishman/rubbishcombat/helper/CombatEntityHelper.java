package com.rubbishman.rubbishcombat.helper;

import com.rubbishman.rubbishcombat.state.CombatEntity;
import com.rubbishman.rubbishcombat.state.attribute.DefensiveAttribute;

public class CombatEntityHelper {
    public static CombatEntity takeDamage(CombatEntity combatEntity, int damageTaken, int defenseTaken) {
        return new CombatEntity(
                Math.max(combatEntity.currentHealth - damageTaken, 0),
                combatEntity.maxHealth,
                new DefensiveAttribute(
                        Math.max(combatEntity.defense.currentDefense - defenseTaken, 0),
                        combatEntity.defense.maxDefense,
                        combatEntity.defense.defenseRatio,
                        combatEntity.defense.regenPeriod,
                        combatEntity.defense.regenFactor
                )
        );
    }
}
