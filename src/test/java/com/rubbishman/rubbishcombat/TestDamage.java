package com.rubbishman.rubbishcombat;

import com.rubbishman.rubbishRedux.external.operational.action.createObject.CreateObject;
import com.rubbishman.rubbishRedux.external.operational.action.createObject.ICreateObjectCallback;
import com.rubbishman.rubbishRedux.external.operational.store.IdObject;
import com.rubbishman.rubbishRedux.external.operational.store.Identifier;
import com.rubbishman.rubbishRedux.internal.dynamicObjectStore.GsonInstance;
import com.rubbishman.rubbishcombat.actions.Damage;
import com.rubbishman.rubbishcombat.state.CombatEntity;
import com.rubbishman.rubbishcombat.state.attribute.DefensiveAttribute;
import com.rubbishman.rubbishcombat.state.attribute.DodgeAttribute;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TestDamage {
    public static Identifier combatEntityId;
    private RubbishCombat rubbishCombat;
    @Before
    public void setup() {
        rubbishCombat = new RubbishCombat();

        createCombatEntity();
    }

    @Test
    public void stupidTest() {
        Damage dmg = new Damage(combatEntityId, 30, 2);

        CombatEntity combatEntity = rubbishCombat.rubbish.getState().getObject(combatEntityId);
        System.out.println(GsonInstance.getInstance().toJson(combatEntity));

        testDamage(dmg);
        testDamage(dmg);
        testDamage(dmg);
        testDamage(dmg);
        testDamage(dmg);

        try {
            Thread.sleep(TimeUnit.MILLISECONDS.convert(5, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n------------------| 5 seconds later |--------------------------\n");

        combatEntity = rubbishCombat.rubbish.getState().getObject(combatEntityId);
        rubbishCombat.rubbish.performActions();
        compareCombatEntities(combatEntity, rubbishCombat.rubbish.getState().getObject(combatEntityId));
    }

    private void testDamage(Damage dmg) {
        CombatEntity combatEntity = rubbishCombat.rubbish.getState().getObject(combatEntityId);
        rubbishCombat.rubbish.performAction(dmg);
        CombatEntity combatEntity2 = rubbishCombat.rubbish.getState().getObject(combatEntityId);
        compareCombatEntities(combatEntity, combatEntity2);
    }

    private void compareCombatEntities(CombatEntity combatEntity, CombatEntity combatEntity2) {
        System.out.println(
                "" +
                        "HP dmg: " + (combatEntity.currentHealth - combatEntity2.currentHealth) +
                        ", armour dmg: " + (combatEntity.defense.currentDefense - combatEntity2.defense.currentDefense) +
                        ", dodge dmg: " + (combatEntity2.dodge.current - combatEntity.dodge.current) +
                        "\n                                                          " +
                        "[ HP: " + combatEntity2.currentHealth+ ", AR: " + combatEntity2.defense.currentDefense + ", DD: " + combatEntity2.dodge.current + " ]");
    }

    private void createCombatEntity() {
        CreateObject<CombatEntity> createObject = new CreateObject<>(standardCombatEntity(),
                new ICreateObjectCallback() {

                    public void postCreateState(Object object) {
                        if(object instanceof IdObject) {
                            combatEntityId = ((IdObject)object).id;
                        }
                    }
                });

        rubbishCombat.rubbish.performAction(createObject);
    }

    private CombatEntity standardCombatEntity() {
        return new CombatEntity(
                100,
                100,
                new DefensiveAttribute(
                        200,
                        200,
                        40,
                        TimeUnit.NANOSECONDS.convert(1, TimeUnit.SECONDS),
                        5
                ),
                new DodgeAttribute(
                        0,
                        5,
                        TimeUnit.NANOSECONDS.convert(1, TimeUnit.SECONDS),
                        10
                ));
    }
}
