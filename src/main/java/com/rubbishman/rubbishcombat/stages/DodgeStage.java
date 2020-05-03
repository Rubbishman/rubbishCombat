package com.rubbishman.rubbishcombat.stages;

import com.rubbishman.rubbishRedux.experimental.actionTrack.stage.StageProcessor;
import com.rubbishman.rubbishRedux.experimental.actionTrack.stage.StageWrapResult;
import com.rubbishman.rubbishRedux.experimental.actionTrack.stage.StageWrappedAction;
import com.rubbishman.rubbishRedux.external.operational.store.ObjectStore;
import com.rubbishman.rubbishcombat.actions.Damage;
import com.rubbishman.rubbishcombat.actions.internalcombat.ArmorDamage;
import com.rubbishman.rubbishcombat.actions.internalcombat.DodgeDamage;
import com.rubbishman.rubbishcombat.state.CombatEntity;

public class DodgeStage implements StageProcessor {
    @Override
    public StageWrapResult processStage(ObjectStore state, StageWrappedAction action) {
        Damage damage = (Damage)action.currentAction;

        CombatEntity combatEntity = state.getObject(damage.target);

        int dodgeTaken = Math.min(damage.damage, combatEntity.dodge.factor);
        int remainingDamageTaken = Math.max(damage.damage - dodgeTaken, 0);

        return new StageWrapResult(
                new Damage(
                        damage.target,
                        remainingDamageTaken,
                        damage.type
                ),
                new DodgeDamage(
                        damage.target,
                        dodgeTaken
                ),
                new DodgeDamage(
                        damage.target,
                        dodgeTaken
                )
        );
    }
}
