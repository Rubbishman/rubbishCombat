package com.rubbishman.rubbishcombat.stages;

import com.rubbishman.rubbishRedux.external.operational.store.ObjectStore;
import com.rubbishman.rubbishRedux.external.setup_extra.actionTrack.stage.StageProcessor;
import com.rubbishman.rubbishRedux.external.setup_extra.actionTrack.stage.StageWrapResult;
import com.rubbishman.rubbishRedux.external.setup_extra.actionTrack.stage.StageWrappedAction;
import com.rubbishman.rubbishcombat.actions.Damage;
import com.rubbishman.rubbishcombat.actions.internalcombat.DirectDamage;

public class DirectDamageStage implements StageProcessor {

    public StageWrapResult processStage(ObjectStore state, StageWrappedAction action) {
        Damage damage = (Damage)action.currentAction;

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
