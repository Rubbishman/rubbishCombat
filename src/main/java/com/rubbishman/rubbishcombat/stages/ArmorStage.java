package com.rubbishman.rubbishcombat.stages;

import com.rubbishman.rubbishRedux.external.operational.store.ObjectStore;
import com.rubbishman.rubbishRedux.external.setup_extra.actionTrack.stage.StageProcessor;
import com.rubbishman.rubbishRedux.external.setup_extra.actionTrack.stage.StageWrapResult;
import com.rubbishman.rubbishRedux.external.setup_extra.actionTrack.stage.StageWrappedAction;
import com.rubbishman.rubbishcombat.actions.Damage;
import com.rubbishman.rubbishcombat.actions.internalcombat.ArmorDamage;
import com.rubbishman.rubbishcombat.state.CombatEntity;

public class ArmorStage implements StageProcessor {
    @Override
    public StageWrapResult processStage(ObjectStore state, StageWrappedAction action) {
        Damage damage = (Damage)action.currentAction;

        CombatEntity combatEntity = state.getObject(damage.target);
        float percentDef = ((float)combatEntity.defense.currentDefense) / (float)combatEntity.defense.maxDefense;
        float fatigueDef = (float)combatEntity.defense.defenseFatigue / (float)combatEntity.defense.maxDefense;

        int defenseTaken = (int)(percentDef * combatEntity.defense.defenseFactor);
        int remainingDamageTaken = Math.max(damage.damage - defenseTaken, 0) + (int)Math.sqrt(fatigueDef * combatEntity.defense.defenseFactor);

        defenseTaken += (int)(fatigueDef * (combatEntity.defense.defenseFactor));

        return new StageWrapResult(
                new Damage(
                        damage.target,
                        remainingDamageTaken,
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
