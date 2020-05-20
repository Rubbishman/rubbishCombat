package com.rubbishman.rubbishcombat.gui;

import com.rubbishman.rubbishRedux.external.operational.action.createObject.CreateObject;
import com.rubbishman.rubbishRedux.external.operational.action.createObject.ICreateObjectCallback;
import com.rubbishman.rubbishRedux.external.operational.store.IdObject;
import com.rubbishman.rubbishRedux.external.operational.store.Identifier;
import com.rubbishman.rubbishcombat.RubbishCombat;
import com.rubbishman.rubbishcombat.gui.components.HealthBarPane;
import com.rubbishman.rubbishcombat.state.CombatEntity;
import com.rubbishman.rubbishcombat.state.attribute.DefensiveAttribute;
import com.rubbishman.rubbishcombat.state.attribute.DodgeAttribute;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

public class Boostrap extends Application {
    public static Identifier combatEntityId;
    private RubbishCombat rubbishCombat;

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        rubbishCombat = new RubbishCombat();
        createCombatEntity();

        rubbishCombat.startTimer();

        HealthBarPane hpPane = new HealthBarPane(stage, rubbishCombat, combatEntityId);

        stage.show();
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
                    15,
                    TimeUnit.NANOSECONDS.convert(500, TimeUnit.MILLISECONDS),
                    0),
            new DodgeAttribute(
                    0,
                    5,
                    TimeUnit.NANOSECONDS.convert(5, TimeUnit.SECONDS),
                    10
            ));
    }
}
