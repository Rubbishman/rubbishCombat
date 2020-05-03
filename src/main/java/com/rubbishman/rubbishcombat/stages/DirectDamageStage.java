package com.rubbishman.rubbishcombat.stages;

import com.rubbishman.rubbishRedux.experimental.actionTrack.stage.StageProcessor;
import com.rubbishman.rubbishRedux.experimental.actionTrack.stage.StageWrapResult;
import com.rubbishman.rubbishRedux.experimental.actionTrack.stage.StageWrappedAction;
import com.rubbishman.rubbishRedux.external.operational.store.ObjectStore;
import com.rubbishman.rubbishcombat.actions.Damage;
import com.rubbishman.rubbishcombat.actions.internalcombat.ArmorDamage;
import com.rubbishman.rubbishcombat.actions.internalcombat.DirectDamage;
import com.rubbishman.rubbishcombat.state.CombatEntity;

public class DirectDamageStage implements StageProcessor {

    public StageWrapResult processStage(ObjectStore state, StageWrappedAction action) {
        Damage damage = (Damage)action.currentAction;

        CombatEntity combatEntity = state.getObject(damage.target);


        return new StageWrapResult(
            null,
            new DirectDamage(
                    damage.target,
                    damage.damage
            ),
            new DirectDamage(
                    damage.target,
                    damage.damage
            )
        );
    }
}
