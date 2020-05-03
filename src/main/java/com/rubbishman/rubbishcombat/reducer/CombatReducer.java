package com.rubbishman.rubbishcombat.reducer;

import com.rubbishman.rubbishRedux.external.operational.store.ObjectStore;
import com.rubbishman.rubbishRedux.external.setup.IRubbishReducer;
import com.rubbishman.rubbishcombat.actions.internalcombat.ArmorDamage;
import com.rubbishman.rubbishcombat.actions.internalcombat.DirectDamage;
import com.rubbishman.rubbishcombat.actions.internalcombat.DodgeDamage;
import com.rubbishman.rubbishcombat.state.CombatEntity;
import com.rubbishman.rubbishcombat.actions.Damage;
import com.rubbishman.rubbishcombat.helper.CombatEntityHelper;

public class CombatReducer extends IRubbishReducer {

    public ObjectStore reduce(ObjectStore state, Object action) {
        if(action instanceof ArmorDamage) {
            ArmorDamage damage = (ArmorDamage)action;
            CombatEntity combatEntity = state.getObject(damage.target);

            state = state.setObject(damage.target, CombatEntityHelper.takeArmorDamage(combatEntity, damage.damage));
        } else if(action instanceof DodgeDamage) {
            DodgeDamage damage = (DodgeDamage)action;
            CombatEntity combatEntity = state.getObject(damage.target);

            state = state.setObject(damage.target, CombatEntityHelper.takeDodgeDamage(combatEntity, damage.damage));
        } else if(action instanceof DirectDamage) {
            DirectDamage damage = (DirectDamage)action;
            CombatEntity combatEntity = state.getObject(damage.target);

            state = state.setObject(damage.target, CombatEntityHelper.takeDirectDamage(combatEntity, damage.damage));
        }

        return state;
    }
}
