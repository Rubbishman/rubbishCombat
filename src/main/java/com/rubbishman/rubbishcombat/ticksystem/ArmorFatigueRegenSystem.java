package com.rubbishman.rubbishcombat.ticksystem;

import com.rubbishman.rubbishRedux.external.operational.store.IdObject;
import com.rubbishman.rubbishRedux.external.operational.store.Identifier;
import com.rubbishman.rubbishRedux.external.setup_extra.TickSystem;
import com.rubbishman.rubbishRedux.external.setup_extra.actionTrack.ActionTrack;
import com.rubbishman.rubbishcombat.actions.internalcombat.ArmorFatigueRegen;
import com.rubbishman.rubbishcombat.actions.internalcombat.ArmorRegen;
import com.rubbishman.rubbishcombat.state.CombatEntity;
import org.organicdesign.fp.collections.PersistentHashMap;

import java.util.function.Consumer;

public class ArmorFatigueRegenSystem extends TickSystem {
    public void beforeDispatchStarted(ActionTrack actionQueue, Long nowTime) {
        Consumer<IdObject> consumer = new Consumer<IdObject>() {
            public void accept(IdObject o) {
                CombatEntity combatEntity = (CombatEntity)o.object;

                if(combatEntity.defense.defenseFatigue > 0) {
                    actionQueue.addAction(new ArmorFatigueRegen(o.id));
                }
            }
        };

        PersistentHashMap<Identifier,IdObject> objects = this.store.getState().getObjectsByClass(CombatEntity.class);
        if(objects != null) {
            objects.valIterator().forEachRemaining(consumer);
        }
    }

    public void afterDispatchFinished() {

    }
}