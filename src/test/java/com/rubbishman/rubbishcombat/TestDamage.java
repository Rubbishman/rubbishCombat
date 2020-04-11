package com.rubbishman.rubbishcombat;

import com.rubbishman.rubbishRedux.external.RubbishContainer;
import com.rubbishman.rubbishRedux.external.operational.action.createObject.CreateObject;
import com.rubbishman.rubbishRedux.external.operational.action.createObject.ICreateObjectCallback;
import com.rubbishman.rubbishRedux.external.operational.store.IdObject;
import com.rubbishman.rubbishRedux.external.operational.store.Identifier;
import com.rubbishman.rubbishRedux.external.setup.RubbishContainerCreator;
import com.rubbishman.rubbishRedux.external.setup.RubbishContainerOptions;
import com.rubbishman.rubbishRedux.internal.dynamicObjectStore.GsonInstance;
import com.rubbishman.rubbishcombat.reducer.CombatReducer;
import com.rubbishman.rubbishcombat.state.CombatEntity;
import com.rubbishman.rubbishcombat.state.attribute.DefensiveAttribute;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestDamage {
    public static Identifier combatEntityId;
    private RubbishContainer rubbish;
    @Before
    public void setup() {
        RubbishContainerOptions options = new RubbishContainerOptions();

        options
                .setReducer(new CombatReducer());

        rubbish = RubbishContainerCreator.getRubbishContainer(options);

        createCombatEntity();
    }

    @Test
    public void stupidTest() {
        Damage dmg = new Damage(combatEntityId, 30, 2);

        CombatEntity combatEntity = rubbish.getState().getObject(combatEntityId);
        System.out.println(GsonInstance.getInstance().toJson(combatEntity));

        testDamage(dmg);
        testDamage(dmg);
        testDamage(dmg);
        testDamage(dmg);
        testDamage(dmg);
    }

    private void testDamage(Damage dmg) {
        CombatEntity combatEntity = rubbish.getState().getObject(combatEntityId);
        rubbish.performAction(dmg);
        CombatEntity combatEntity2 = rubbish.getState().getObject(combatEntityId);

        System.out.println(
                "HP dmg: " + (combatEntity.currentHealth - combatEntity2.currentHealth) +
                ", armour dmg: " + (combatEntity.defense.currentDefense - combatEntity2.defense.currentDefense));
        System.out.println(GsonInstance.getInstance().toJson(combatEntity2));
        combatEntity = combatEntity2;
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

        rubbish.performAction(createObject);
    }

    private CombatEntity standardCombatEntity() {
        return new CombatEntity(
                100,
                100,
                new DefensiveAttribute(
                        300,
                        300,
                        0.75f,
                        5,
                        100
                )
        );
    }
}
