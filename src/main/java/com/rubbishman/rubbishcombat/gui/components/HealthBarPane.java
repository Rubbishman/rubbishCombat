package com.rubbishman.rubbishcombat.gui.components;

import com.rubbishman.rubbishRedux.external.operational.store.Identifier;
import com.rubbishman.rubbishcombat.RubbishCombat;
import com.rubbishman.rubbishcombat.actions.Damage;
import com.rubbishman.rubbishcombat.actions.internalcombat.HealthRegen;
import com.rubbishman.rubbishcombat.gui.data.CombatText;
import com.rubbishman.rubbishcombat.gui.data.TempData;
import com.rubbishman.rubbishcombat.gui.helper.TweenHelper;
import com.rubbishman.rubbishcombat.state.CombatEntity;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;

public class HealthBarPane {
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    public static final int HEALTH_X = 50;
    public static final int HEALTH_Y = 20;

    public static final int HEALTH_WIDTH = 50;
    public static final int HEALTH_HEIGHT = 440;

    private static TempData tempData;

    private BorderPane northSouth;
    private Canvas canvas;

    private RubbishCombat rubbishCombat;
    private Identifier combatEntityId;

    private HealthBar healthBar = new HealthBar();
    private ArmorBar armorBar = new ArmorBar();
    private DodgeBar dodgeBar = new DodgeBar();

    private ArrayList<ICombatTextRenderer> combatTextRendererArrayList = new ArrayList();

    public HealthBarPane(Stage primaryStage, RubbishCombat rubbishCombat, Identifier combatEntityId) {
        this.rubbishCombat = rubbishCombat;
        this.combatEntityId = combatEntityId;

        CombatEntity combatEntity = rubbishCombat.rubbish.getState().getObject(combatEntityId);

        tempData = new TempData(
                combatEntity.maxHealth,
                new TweenHelper(combatEntity.currentHealth, combatEntity.currentHealth, 0, 40, 0.05),
                combatEntity.defense.maxDefense,
                new TweenHelper(combatEntity.defense.currentDefense, combatEntity.defense.currentDefense, 0, 40, 0.1),
                combatEntity.dodge.current,
                combatEntity.defense.defenseDamage
        );

        canvas = new Canvas(WIDTH, HEIGHT);
        northSouth = new BorderPane();
        primaryStage.setScene(new Scene(northSouth, WIDTH + 100, HEIGHT + 100));

        northSouth.setCenter(canvas);

        addButtons();

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if(event.getCode() == KeyCode.A
                    || event.getCode() == KeyCode.S
                    || event.getCode() == KeyCode.D) {
                    rubbishCombat.rubbish.addAction(new HealthRegen(combatEntityId, 10));
                } else if(event.getCode() == KeyCode.J
                        || event.getCode() == KeyCode.K
                        || event.getCode() == KeyCode.L) {
                    rubbishCombat.rubbish.addAction(new Damage(combatEntityId, 30, 2));
                }
            }
        });

        moo();
    }

    private void addButtons() {
        FlowPane flow = new FlowPane();
        flow.setPadding(new Insets(10, 10, 10, 10));
        flow.setStyle("-fx-background-color: DAE6F3;");
        flow.setHgap(5);

        northSouth.setBottom(flow);

        Button hpIncrease = new Button("HP up");
        Button hpDecrease = new Button("Deal damage");

        flow.getChildren().addAll(
                hpIncrease,
                hpDecrease
        );

        hpIncrease.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                rubbishCombat.rubbish.addAction(new HealthRegen(combatEntityId, 20));
            }
        });

        hpDecrease.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                rubbishCombat.rubbish.addAction(new Damage(combatEntityId, 20, 2));

                int fullDamage = (int)(Math.random() * 30 + 10);
                int armorDamage = (int)(Math.random() * fullDamage);
                int dodgeDamage = (int)(Math.random() * (fullDamage - armorDamage));

                double x = Math.random() * 100 + 200;
                double y = Math.random() * 150 + 300;

                combatTextRendererArrayList.add(
                        new CombatTextRendererPie(
                                new CombatText(x, y, fullDamage, armorDamage, dodgeDamage)
                        )
                );

//                if(Math.random() < 0.5) {
//                    combatTextRendererArrayList.add(
//                            new CombatTextRendererBar(
//                                    new CombatText(x, y, fullDamage, armorDamage, dodgeDamage)
//                            )
//                    );
//                } else {
//                    combatTextRendererArrayList.add(
//                            new CombatTextRendererPie(
//                                    new CombatText(x, y, fullDamage, armorDamage, dodgeDamage)
//                            )
//                    );
//                }
            }
        });
    }

    private void moo() {
        new AnimationTimer() {
            public void handle(long l) {
                updateTempData();// This needs to move to a TickSystem...

                GraphicsContext g2d = canvas.getGraphicsContext2D();
                g2d.clearRect(0,0, WIDTH, HEIGHT);

                healthBar.healthBar(tempData, g2d);

                dodgeBar.dodgeDamage(tempData, g2d);

                armorBar.armorBar(tempData, g2d);

                healthBar.finalBorder(g2d);

                Iterator<ICombatTextRenderer> iter = combatTextRendererArrayList.iterator();

                while(iter.hasNext()) {
                    ICombatTextRenderer combatTextRenderer = iter.next();

                    if(combatTextRenderer.isTerminated(l)) {
                        iter.remove();
                    } else {
                        combatTextRenderer.run(l);
                        combatTextRenderer.draw(g2d);
                    }
                }

                tempData.curHealth.run();
                tempData.curArmour.run();
            }
        }.start();
    }

    private void updateTempData() {
        CombatEntity combatEntity = rubbishCombat.rubbish.getState().getObject(combatEntityId);

        tempData.curHealth.targetValue = combatEntity.currentHealth;
        tempData.curArmour.targetValue = combatEntity.defense.currentDefense;
        tempData.dodgeDamage = combatEntity.dodge.current;
        tempData.armorDamage = combatEntity.defense.defenseDamage;
    }
}
