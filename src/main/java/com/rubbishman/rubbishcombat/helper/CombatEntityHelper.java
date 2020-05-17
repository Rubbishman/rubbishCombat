package com.rubbishman.rubbishcombat.helper;

import com.rubbishman.rubbishcombat.state.CombatEntity;
import com.rubbishman.rubbishcombat.state.attribute.DefensiveAttribute;
import com.rubbishman.rubbishcombat.state.attribute.DodgeAttribute;

import java.util.concurrent.TimeUnit;

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

    public static CombatEntity regenArmor(CombatEntity combatEntity, long elapsedTime) {
        double regen = ((double)elapsedTime / combatEntity.defense.regenPeriod) * combatEntity.defense.regenFactor;
        int intRegen = (int)regen;
        double remainder = regen - intRegen;

        double newRemainder = combatEntity.defense.regenRounding + remainder;
        int newRemainderRounded = (int)newRemainder;

        if(newRemainderRounded > 0) {
            newRemainder -= newRemainderRounded;
            intRegen += newRemainderRounded;
        }

        return new CombatEntity(
                combatEntity.currentHealth,
                combatEntity.maxHealth,
                new DefensiveAttribute(
                        Math.min(combatEntity.defense.currentDefense + intRegen, combatEntity.defense.maxDefense),
                        combatEntity.defense.maxDefense,
                        combatEntity.defense.defenseFactor,
                        combatEntity.defense.regenPeriod,
                        combatEntity.defense.regenFactor,
                        newRemainder
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

    public static CombatEntity resolveDodgeDamage(CombatEntity combatEntity, int damageTaken) {
        return new CombatEntity(
                Math.max(combatEntity.currentHealth - damageTaken, 0),
                combatEntity.maxHealth,
                combatEntity.defense,
                new DodgeAttribute(
                        combatEntity.dodge.current - damageTaken,
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

    public static CombatEntity healthRegen(CombatEntity combatEntity, int amount) {
        return new CombatEntity(
                Math.min(combatEntity.currentHealth + amount, combatEntity.maxHealth),
                combatEntity.maxHealth,
                combatEntity.defense,
                combatEntity.dodge);
    }
}
