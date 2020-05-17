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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TestDamage {
    public static Identifier combatEntityId;
    private RubbishCombat rubbishCombat;
    private TestTimeKeeper testTimeKeeper;
    @Before
    public void setup() {
        testTimeKeeper = new TestTimeKeeper(0);
        rubbishCombat = new RubbishCombat(testTimeKeeper);

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

        rubbishCombat.rubbish.performActions();// This is because of a bug...

        assertDodgeDamageValue(12);

        progressTimeTo(TimeUnit.NANOSECONDS.convert(1, TimeUnit.SECONDS) / 3);
        assertDodgeDamageValue(10);

        progressTimeTo(TimeUnit.NANOSECONDS.convert(1, TimeUnit.SECONDS) / 3 * 2);
        assertDodgeDamageValue(5);

        progressTimeTo(TimeUnit.NANOSECONDS.convert(1, TimeUnit.SECONDS));

        assertDodgeDamageValue(0);
    }

    private void progressTimeTo(long value) {
        CombatEntity combatEntity = rubbishCombat.rubbish.getState().getObject(combatEntityId);
        testTimeKeeper.setNewTime(value);
        rubbishCombat.rubbish.performActions();
        compareCombatEntities(combatEntity, rubbishCombat.rubbish.getState().getObject(combatEntityId));
    }

    private void assertDodgeDamageValue(int value) {
        CombatEntity combatEntity = rubbishCombat.rubbish.getState().getObject(combatEntityId);
        Assert.assertEquals(value,combatEntity.dodge.current);
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
                        5,
                        TimeUnit.NANOSECONDS.convert(500, TimeUnit.MILLISECONDS),
                        0
                ),
                new DodgeAttribute(
                        0,
                        5,
                        TimeUnit.NANOSECONDS.convert(1, TimeUnit.SECONDS),
                        10
                ));
    }
}
