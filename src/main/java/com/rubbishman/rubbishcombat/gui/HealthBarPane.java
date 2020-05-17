package com.rubbishman.rubbishcombat.gui;

import com.rubbishman.rubbishRedux.external.operational.store.Identifier;
import com.rubbishman.rubbishcombat.RubbishCombat;
import com.rubbishman.rubbishcombat.actions.Damage;
import com.rubbishman.rubbishcombat.actions.internalcombat.HealthRegen;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
                rubbishCombat.rubbish.addAction(new HealthRegen(combatEntityId, 10));
            }
        });

        hpDecrease.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                rubbishCombat.rubbish.addAction(new Damage(combatEntityId, 30, 2));
            }
        });
    }

    private void moo() {
        new AnimationTimer() {
            public void handle(long l) {
                updateTempData();

                GraphicsContext g2d = canvas.getGraphicsContext2D();
                g2d.clearRect(0,0, WIDTH, HEIGHT);

                healthBar(g2d);

                dodgeDamage(g2d);

                armorBar(g2d);

                armorDamage(g2d);

                finalBorder(g2d);

                tempData.curHealth.run();
                tempData.curArmour.run();
            }
        }.start();
    }

    private void healthBar(GraphicsContext g2d) {
        g2d.setFill(new Color(0.1,0.7,0.3, 1));

        // Health bar
        g2d.fillRect(
                HEALTH_X,
                HEALTH_Y + (HEALTH_HEIGHT - (((double)tempData.curHealth.curValue / (double)tempData.maxHealth) * HEALTH_HEIGHT)),
                HEALTH_WIDTH,
                (((double)tempData.curHealth.curValue / (double)tempData.maxHealth) * HEALTH_HEIGHT)
        );

        if(tempData.curHealth.curValue != tempData.curHealth.targetValue) {
            g2d.setFill(new Color(0.118,0.408,0.047, 1));

            // Health bar
            g2d.fillRect(
                    HEALTH_X,
                    HEALTH_Y + (HEALTH_HEIGHT - (((double)Math.max(tempData.curHealth.curValue, tempData.curHealth.targetValue) / (double)tempData.maxHealth) * HEALTH_HEIGHT)),
                    HEALTH_WIDTH,
                    (((double)Math.abs(tempData.curHealth.curValue-tempData.curHealth.targetValue) / (double)tempData.maxHealth) * HEALTH_HEIGHT)
            );
        }
    }

    private void dodgeDamage(GraphicsContext g2d) {
        g2d.setFill(new Color(0.8,0.1,0.2, 0.7));

        double dodgeDamageStartY = HEALTH_Y + (HEALTH_HEIGHT - ((((double)tempData.curHealth.targetValue) / (double)tempData.maxHealth) * HEALTH_HEIGHT));
        double dodgeDamageHeight = ((((double)tempData.dodgeDamage) / (double)tempData.maxHealth) * HEALTH_HEIGHT);

        g2d.fillRect(
                HEALTH_X + (HEALTH_WIDTH / 4),
                dodgeDamageStartY,
                (double)HEALTH_WIDTH / 4 * 3,
                dodgeDamageHeight
        );
    }

    private void armorDamage(GraphicsContext g2d) {
        g2d.setFill(new Color(0.8,0.1,0.2, 0.7));

        double dodgeDamageStartY = HEALTH_Y + (HEALTH_HEIGHT - ((((double)tempData.curArmour.curValue) / (double)tempData.maxArmour) * HEALTH_HEIGHT));
        double dodgeDamageHeight = ((((double)tempData.armorDamage) / (double)tempData.maxArmour) * HEALTH_HEIGHT);

        g2d.fillRect(
                HEALTH_X + (HEALTH_WIDTH / 8 * 6),
                dodgeDamageStartY,
                (double)HEALTH_WIDTH / 8,
                dodgeDamageHeight
        );
    }

    private void armorBar(GraphicsContext g2d) {
        g2d.setStroke(new Color(0,0,0,0.7));

        g2d.setFill(new Color(0.9F, 0.9F, 0.3F, 0.8));
        g2d.fillRect(
                HEALTH_X + ((double)HEALTH_WIDTH / 4 * 3),
                HEALTH_Y + (HEALTH_HEIGHT - (((double)tempData.curArmour.curValue / (double)tempData.maxArmour) * HEALTH_HEIGHT)),
                (double)HEALTH_WIDTH / 4,
                (((double)tempData.curArmour.curValue / (double)tempData.maxArmour) * HEALTH_HEIGHT)
        );
        g2d.strokeRect(
                HEALTH_X + ((double)HEALTH_WIDTH / 4 * 3),
                HEALTH_Y + (HEALTH_HEIGHT - (((double)tempData.curArmour.curValue / (double)tempData.maxArmour) * HEALTH_HEIGHT)),
                (double)HEALTH_WIDTH / 4,
                (((double)tempData.curArmour.curValue / (double)tempData.maxArmour) * HEALTH_HEIGHT));

        if(tempData.curArmour.curValue != tempData.curArmour.targetValue) {
            g2d.setFill(new Color(0.6,0.6,0.3, 1));

            // Health bar
            g2d.fillRect(
                    HEALTH_X + ((double)HEALTH_WIDTH / 4 * 3),
                    HEALTH_Y + (HEALTH_HEIGHT - (((double)Math.max(tempData.curArmour.curValue, tempData.curArmour.targetValue) / (double)tempData.maxArmour) * HEALTH_HEIGHT)),
                    (double)HEALTH_WIDTH / 4,
                    (((double)Math.abs(tempData.curArmour.curValue-tempData.curArmour.targetValue) / (double)tempData.maxArmour) * HEALTH_HEIGHT)
            );

            g2d.strokeRect(
                    HEALTH_X + ((double)HEALTH_WIDTH / 4 * 3),
                    HEALTH_Y + (HEALTH_HEIGHT - (((double)Math.max(tempData.curArmour.curValue, tempData.curArmour.targetValue) / (double)tempData.maxArmour) * HEALTH_HEIGHT)),
                    (double)HEALTH_WIDTH / 4,
                    (((double)Math.abs(tempData.curArmour.curValue-tempData.curArmour.targetValue) / (double)tempData.maxArmour) * HEALTH_HEIGHT));
        }
    }

    private void finalBorder(GraphicsContext g2d) {
        g2d.setStroke(Color.BLACK);
        g2d.strokeRect(HEALTH_X, HEALTH_Y, HEALTH_WIDTH, HEALTH_HEIGHT);
    }

    private void updateTempData() {
        CombatEntity combatEntity = rubbishCombat.rubbish.getState().getObject(combatEntityId);

        tempData.curHealth.targetValue = combatEntity.currentHealth;
        tempData.curArmour.targetValue = combatEntity.defense.currentDefense;
        tempData.dodgeDamage = combatEntity.dodge.current;
        tempData.armorDamage = combatEntity.defense.defenseDamage;
    }
}
