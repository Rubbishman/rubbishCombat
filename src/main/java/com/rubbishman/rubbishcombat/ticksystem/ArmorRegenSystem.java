package com.rubbishman.rubbishcombat.ticksystem;

import com.rubbishman.rubbishRedux.external.operational.store.IdObject;
import com.rubbishman.rubbishRedux.external.operational.store.Identifier;
import com.rubbishman.rubbishRedux.external.setup_extra.TickSystem;
import com.rubbishman.rubbishRedux.external.setup_extra.actionTrack.ActionTrack;
import com.rubbishman.rubbishcombat.actions.internalcombat.ArmorRegen;
import com.rubbishman.rubbishcombat.state.CombatEntity;
import org.organicdesign.fp.collections.PersistentHashMap;

import java.util.function.Consumer;

public class ArmorRegenSystem extends TickSystem {
    public void beforeDispatchStarted(ActionTrack actionQueue, Long nowTime) {
        Consumer<IdObject> consumer = new Consumer<IdObject>() {
            public void accept(IdObject o) {
                CombatEntity combatEntity = (CombatEntity)o.object;

                if(combatEntity.defense.currentDefense < combatEntity.defense.maxDefense) {
                    actionQueue.addAction(new ArmorRegen(o.id));
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
