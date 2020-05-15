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

            long period = combatEntity.dodge.period / damage.damage;
            long repeats = combatEntity.dodge.period / period;

            rubbishContainer.createTimer(
                    rubbishContainer.getNowTime(),
                    new DodgeDamageOTResolution(createStore.createdObject.id, 1),
                    period,
                    repeats
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

            state = state.setObject(ddot.target, CombatEntityHelper.resolveDodgeDamage(combatEntity, ddotRes.damage));
            state = state.setObject(ddotRes.target, new DodgeDamageOT(
                    ddot.target,
                    ddot.startTime,
                    getNowTime(),
                    ddot.damage - ddotRes.damage,
                    ddot.period
            ));
        }

        return state;
    }
}
