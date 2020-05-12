package com.rubbishman.rubbishcombat.reducer;

import com.rubbishman.rubbishRedux.external.operational.store.ObjectStore;
import com.rubbishman.rubbishRedux.external.setup.IRubbishReducer;
import com.rubbishman.rubbishRedux.internal.dynamicObjectStore.store.CreatedObjectStore;
import com.rubbishman.rubbishcombat.actions.internalcombat.*;
import com.rubbishman.rubbishcombat.state.CombatEntity;
import com.rubbishman.rubbishcombat.actions.Damage;
import com.rubbishman.rubbishcombat.helper.CombatEntityHelper;
import com.rubbishman.rubbishcombat.state.affects.DodgeDamageOT;

import java.util.concurrent.TimeUnit;

public class CombatReducer extends IRubbishReducer {

    public ObjectStore reduce(ObjectStore state, Object action) {
        if(action instanceof ArmorDamage) {
            ArmorDamage damage = (ArmorDamage)action;
            CombatEntity combatEntity = state.getObject(damage.target);

            state = state.setObject(damage.target, CombatEntityHelper.takeArmorDamage(combatEntity, damage.damage));
        } else if(action instanceof DodgeDamage) {
            DodgeDamage damage = (DodgeDamage)action;
            CombatEntity combatEntity = state.getObject(damage.target);
            if(damage.damage == 0) {
                return state;
            }
            state = state.setObject(damage.target, CombatEntityHelper.takeDodgeDamage(combatEntity, damage.damage));
            CreatedObjectStore createStore = state.createObject(
                    new DodgeDamageOT(
                            damage.target,
                            rubbishContainer.getNowTime(),
                            rubbishContainer.getNowTime(),
                            damage.damage,
                            combatEntity.dodge.period
                    )
            );

            rubbishContainer.createTimer(
                    rubbishContainer.getNowTime(),
                    new DodgeDamageOTResolution(createStore.createdObject.id),
                    TimeUnit.NANOSECONDS.convert(30, TimeUnit.MILLISECONDS),
                    combatEntity.dodge.period / TimeUnit.NANOSECONDS.convert(30, TimeUnit.MILLISECONDS)
            );

            state = createStore.state;
        } else if(action instanceof DirectDamage) {
            DirectDamage damage = (DirectDamage)action;
            CombatEntity combatEntity = state.getObject(damage.target);

            state = state.setObject(damage.target, CombatEntityHelper.takeDirectDamage(combatEntity, damage.damage));
        } else if(action instanceof ArmorRegen) {
            ArmorRegen regen = (ArmorRegen)action;
            CombatEntity combatEntity = state.getObject(regen.target);

            state = state.setObject(regen.target, CombatEntityHelper.regenArmor(combatEntity, getElapsedTime()));
        } else if (action instanceof DodgeDamageOTResolution) {
            DodgeDamageOTResolution ddotRes = (DodgeDamageOTResolution)action;

            DodgeDamageOT ddot = state.getObject(ddotRes.target);

            CombatEntity combatEntity = state.getObject(ddot.target);

            long diff = Math.min(ddot.startTime + ddot.period, getNowTime()) - ddot.curTime;

            double damage = ((double)diff / ddot.period) * ddot.damage;
            int intDmg = (int)damage;
            double remainder = damage - intDmg;
            state = state.setObject(ddot.target, CombatEntityHelper.takeDirectDamage(combatEntity, intDmg));
            state = state.setObject(ddotRes.target, new DodgeDamageOT(
                    ddot.target,
                    ddot.startTime,
                    getNowTime(),
                    ddot.damage - intDmg,
                    ddot.period,
                    remainder
            ));
        }

        return state;
    }
}
