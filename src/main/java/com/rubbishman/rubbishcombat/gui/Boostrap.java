package com.rubbishman.rubbishcombat.gui;

import com.rubbishman.rubbishRedux.external.operational.action.createObject.CreateObject;
import com.rubbishman.rubbishRedux.external.operational.action.createObject.ICreateObjectCallback;
import com.rubbishman.rubbishRedux.external.operational.store.IdObject;
import com.rubbishman.rubbishRedux.external.operational.store.Identifier;
import com.rubbishman.rubbishRedux.external.setup_extra.actionTrack.ActionTrackListener;
import com.rubbishman.rubbishcombat.RubbishCombat;
import com.rubbishman.rubbishcombat.actions.internalcombat.ArmorDamage;
import com.rubbishman.rubbishcombat.actions.internalcombat.DirectDamage;
import com.rubbishman.rubbishcombat.actions.internalcombat.DodgeDamage;
import com.rubbishman.rubbishcombat.gui.components.CombatTextRendererBar;
import com.rubbishman.rubbishcombat.gui.components.CombatTextRendererPie;
import com.rubbishman.rubbishcombat.gui.components.HealthBarPane;
import com.rubbishman.rubbishcombat.gui.components.ICombatTextRenderer;
import com.rubbishman.rubbishcombat.gui.data.CombatText;
import com.rubbishman.rubbishcombat.gui.data.TempData;
import com.rubbishman.rubbishcombat.state.CombatEntity;
import com.rubbishman.rubbishcombat.state.attribute.DefensiveAttribute;
import com.rubbishman.rubbishcombat.state.attribute.DodgeAttribute;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class Boostrap extends Application {
    public static Identifier combatEntityId;
    private RubbishCombat rubbishCombat;
    private final TempData tempData = new TempData();
    private ArrayList<ICombatTextRenderer> combatTextRendererArrayList = new ArrayList();

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        rubbishCombat = new RubbishCombat(new Runnable() {
                @Override
                public void run() {
                    CombatEntity combatEntity = rubbishCombat.rubbish.getState().getObject(combatEntityId);

                    tempData.curHealth.targetValue = combatEntity.currentHealth;
                    tempData.curArmour.targetValue = combatEntity.defense.currentDefense;
                    tempData.dodgeDamage = combatEntity.dodge.current;
                    tempData.armorDamage = combatEntity.defense.defenseDamage;
                }
            },
            new ActionTrackListener() {
                @Override
                public void listen(HashMap<Long, Object> stageResults) {
                    double x = Math.random() * 400 + 150;
                    double y = Math.random() * 300 + 80;

                    ArmorDamage armorDamage = (ArmorDamage)stageResults.get(rubbishCombat.armor.priority);
                    DodgeDamage dodgeDamage = (DodgeDamage) stageResults.get(rubbishCombat.dodge.priority);
                    DirectDamage directDamage = (DirectDamage) stageResults.get(rubbishCombat.direct.priority);

                    int fullDamage = directDamage.damage + armorDamage.damage + dodgeDamage.damage;

                    if(Math.random() > 0.5) {
                        combatTextRendererArrayList.add(
                                new CombatTextRendererPie(
                                        new CombatText(x, y, fullDamage, armorDamage.damage, dodgeDamage.damage)
                                )
                        );
                    } else {
                        combatTextRendererArrayList.add(
                                new CombatTextRendererBar(
                                        new CombatText(x, y, fullDamage, armorDamage.damage, dodgeDamage.damage)
                                )
                        );
                    }
                }
            }
            );
        createCombatEntity();

        rubbishCombat.startTimer();

        HealthBarPane hpPane = new HealthBarPane(stage, rubbishCombat, combatEntityId, tempData);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                Iterator<ICombatTextRenderer> iter = combatTextRendererArrayList.iterator();
                while(iter.hasNext()) {
                    hpPane.combatTextRendererArrayList.add(iter.next());
                    iter.remove();
                }
            }
        }.start();

        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent t) {
                System.exit(0);
            }
        });
    }

    private void createCombatEntity() {
        CreateObject<CombatEntity> createObject = new CreateObject<>(standardCombatEntity(),
                new ICreateObjectCallback() {

                    public void postCreateState(Object object) {
                        if(object instanceof IdObject) {
                            combatEntityId = ((IdObject)object).id;
                            tempData.updateFromCombatEntity((CombatEntity)((IdObject) object).object);
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
                    20,
                    TimeUnit.NANOSECONDS.convert(1, TimeUnit.SECONDS),
                    15,
                    TimeUnit.NANOSECONDS.convert(500, TimeUnit.MILLISECONDS),
                    0),
            new DodgeAttribute(
                    0,
                    8,
                    TimeUnit.NANOSECONDS.convert(5, TimeUnit.SECONDS),
                    10
            ));
    }
}
