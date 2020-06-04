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
import javafx.scene.paint.Color;
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

    public ArrayList<ICombatTextRenderer> combatTextRendererArrayList = new ArrayList();

    public HealthBarPane(Stage primaryStage, RubbishCombat rubbishCombat, Identifier combatEntityId, TempData tempData) {
        this.tempData = tempData;
        this.rubbishCombat = rubbishCombat;
        this.combatEntityId = combatEntityId;

        CombatEntity combatEntity = rubbishCombat.rubbish.getState().getObject(combatEntityId);

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
            }
        });
    }

    public void moo() {
        new AnimationTimer() {
            public void handle(long l) {
//                updateTempData();// This needs to move to a TickSystem...

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

                float smHeight = 200f;
                float smWidth = 200f;
                float start = 150f;
                float riseStart = 200f;
                float riseLength = 40f;
                float topShelfLength = 5f;
                float fallLength = 30f;
                for(float x = start; x <= start + smWidth; x = x + 0.001f) {
                    g2d.setStroke(Color.RED);
                    float y = boostWindow(riseStart, riseLength, topShelfLength, fallLength, x);
                    g2d.strokeLine(x + start,  300 - (y * smHeight), x + start, 300 - (y * smHeight));
                }
            }
        }.start();
    }

    float boostWindow(float riseStart, float riseLength, float topShelfLength, float fallLength, float x) {
        if(x < riseStart || x > riseStart + riseLength + fallLength + topShelfLength) {
            return 0;
        } else if(x >= riseStart + riseLength && x <= riseStart + riseLength + topShelfLength) {
            return 1;
        } else if(x >= riseStart && x <= riseStart + riseLength) {
            return smoothstep(0, 1, (x - riseStart) / riseLength);
        } else {
            return smoothstep(1, 0, (x - riseStart - topShelfLength - riseLength) / (fallLength));
        }
    }

    float smootherstep(float edge0, float edge1, float x) {
        // Scale, and clamp x to 0..1 range
        x = clamp((x - edge0) / (edge1 - edge0), 0.0f, 1.0f);
        // Evaluate polynomial
        return x * x * x * (x * (x * 6 - 15) + 10);
    }

    public float smoothstep(float edge0, float edge1, float x) {
        // Scale, bias and saturate x to 0..1 range
        x = clamp((x - edge0) / (edge1 - edge0), 0.0f, 1.0f);
        // Evaluate polynomial
        return x * x * (3 - 2 * x);
    }

    public float clamp(float x, float lowerlimit, float upperlimit) {
        if (x < lowerlimit)
            x = lowerlimit;
        if (x > upperlimit)
            x = upperlimit;
        return x;
    }
}
