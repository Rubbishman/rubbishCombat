package com.rubbishman.rubbishcombat.reducer;

import com.rubbishman.rubbishRedux.external.operational.store.ObjectStore;
import com.rubbishman.rubbishRedux.external.setup.IRubbishReducer;
import com.rubbishman.rubbishcombat.state.CombatEntity;
import com.rubbishman.rubbishcombat.actions.Damage;
import com.rubbishman.rubbishcombat.helper.CombatEntityHelper;

public class CombatReducer extends IRubbishReducer {

    public ObjectStore reduce(ObjectStore state, Object action) {
        if(action instanceof Damage) {
            Damage damage = (Damage)action;
            CombatEntity combatEntity = state.getObject(damage.target);

            state = state.setObject(damage.target, calculateDamage(combatEntity, damage));
        }
        return state;
    }

    public CombatEntity calculateDamage(CombatEntity combatEntity, Damage damage) {
        float percentDef = (float)combatEntity.defense.currentDefense / (float)combatEntity.defense.maxDefense;

        int defenseTaken = (int)(percentDef * combatEntity.defense.defenseRatio * damage.damage);

        int damageTaken = Math.max(damage.damage - defenseTaken, 0);

        return CombatEntityHelper.takeDamage(combatEntity, damageTaken, defenseTaken);
    }
}
