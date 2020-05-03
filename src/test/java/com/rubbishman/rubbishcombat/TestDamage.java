package com.rubbishman.rubbishcombat;

import com.google.common.collect.ImmutableList;
import com.rubbishman.rubbishRedux.experimental.actionTrack.stage.StageWrap;
import com.rubbishman.rubbishRedux.external.RubbishContainer;
import com.rubbishman.rubbishRedux.external.operational.action.createObject.CreateObject;
import com.rubbishman.rubbishRedux.external.operational.action.createObject.ICreateObjectCallback;
import com.rubbishman.rubbishRedux.external.operational.action.multistageAction.Stage.Stage;
import com.rubbishman.rubbishRedux.external.operational.store.IdObject;
import com.rubbishman.rubbishRedux.external.operational.store.Identifier;
import com.rubbishman.rubbishRedux.external.setup.RubbishContainerCreator;
import com.rubbishman.rubbishRedux.external.setup.RubbishContainerOptions;
import com.rubbishman.rubbishRedux.internal.dynamicObjectStore.GsonInstance;
import com.rubbishman.rubbishcombat.actions.Damage;
import com.rubbishman.rubbishcombat.reducer.CombatReducer;
import com.rubbishman.rubbishcombat.stages.ArmorStage;
import com.rubbishman.rubbishcombat.stages.DirectDamageStage;
import com.rubbishman.rubbishcombat.stages.DodgeStage;
import com.rubbishman.rubbishcombat.state.CombatEntity;
import com.rubbishman.rubbishcombat.state.attribute.DefensiveAttribute;
import com.rubbishman.rubbishcombat.state.attribute.DodgeAttribute;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestDamage {
    public static Identifier combatEntityId;
    private RubbishContainer rubbish;
    @Before
    public void setup() {
        RubbishContainerOptions options = new RubbishContainerOptions();

        try {
            Stage armor = options.createStage("armor");
            Stage dodge = options.createStage("dodge");
            Stage direct = options.createStage("direct");

            options.setStageProcessor(Damage.class,
                ImmutableList.of(
                        new StageWrap(armor, new ArmorStage()),
                        new StageWrap(dodge, new DodgeStage()),
                        new StageWrap(direct, new DirectDamageStage())
                )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                " [ " + combatEntity2.currentHealth+ ", " + combatEntity2.defense.currentDefense + ", " + combatEntity2.dodge.current + " ]\n                " +
                "HP dmg: " + (combatEntity.currentHealth - combatEntity2.currentHealth) +
                ", armour dmg: " + (combatEntity.defense.currentDefense - combatEntity2.defense.currentDefense) +
                ", dodge dmg: " + (combatEntity2.dodge.current - combatEntity.dodge.current));
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
                        200,
                        200,
                        40,
                        5,
                        100
                ),
                new DodgeAttribute(
                        0,
                        5,
                        1000,
                        10
                ));
    }
}
