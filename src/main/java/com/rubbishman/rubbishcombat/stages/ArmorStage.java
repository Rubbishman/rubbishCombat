package com.rubbishman.rubbishcombat.stages;

import com.rubbishman.rubbishRedux.experimental.actionTrack.stage.StageProcessor;
import com.rubbishman.rubbishRedux.experimental.actionTrack.stage.StageWrapResult;
import com.rubbishman.rubbishRedux.experimental.actionTrack.stage.StageWrappedAction;
import com.rubbishman.rubbishRedux.external.operational.store.ObjectStore;
import com.rubbishman.rubbishcombat.actions.Damage;
import com.rubbishman.rubbishcombat.actions.internalcombat.ArmorDamage;
import com.rubbishman.rubbishcombat.state.CombatEntity;

public class ArmorStage implements StageProcessor {
    @Override
    public StageWrapResult processStage(ObjectStore state, StageWrappedAction action) {
        Damage damage = (Damage)action.currentAction;

        CombatEntity combatEntity = state.getObject(damage.target);
        float percentDef = (float)combatEntity.defense.currentDefense / (float)combatEntity.defense.maxDefense;

        int defenseTaken = Math.min(damage.damage, (int)(percentDef * combatEntity.defense.defenseFactor));
        int damageTaken = Math.max(damage.damage - defenseTaken, 0);

        return new StageWrapResult(
                new Damage(
                        damage.target,
                        damageTaken,
                        damage.type
                ),
                new ArmorDamage(
                        damage.target,
                        defenseTaken
                ),
                new ArmorDamage(
                        damage.target,
                        defenseTaken
                )
        );
    }
}
