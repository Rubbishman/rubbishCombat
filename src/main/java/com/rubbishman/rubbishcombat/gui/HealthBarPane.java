package com.rubbishman.rubbishcombat.gui;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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

    public static final int DODGE_DAMAGE_INC = 250;

    private static TempData tempData;

    private static TweenHelper tween;

    private BorderPane northSouth;
    private Canvas canvas;

    public HealthBarPane(Stage primaryStage) {
        tempData = new TempData(
                100,
                new TweenHelper(47, 47, 0, 40, 0.01),
                200,
                150,
                40,
                1500
        );

        tween = new TweenHelper(
            0,
            HEIGHT,
                0,
                10,
                0.1
        );

        canvas = new Canvas(WIDTH, HEIGHT);
        northSouth = new BorderPane();
        primaryStage.setScene(new Scene(northSouth, WIDTH + 100, HEIGHT + 100));

        northSouth.setCenter(canvas);

        addButtons();

        moo();
    }

    private void addButtons() {
        FlowPane flow = new FlowPane();
        flow.setPadding(new Insets(10, 10, 10, 10));
        flow.setStyle("-fx-background-color: DAE6F3;");
        flow.setHgap(5);

        northSouth.setBottom(flow);

        Button hpIncrease = new Button("HP up");
        Button hpDecrease = new Button("Hp down");
        Button dodgeDamageIncrease = new Button("Add dodge damage");
        Button dodgeDamageDecrease = new Button("Reduce dodge damage");

        Button dodgePeriodIncrease = new Button("Dodge period increase");
        Button dodgePeriodDecrease = new Button("Dodge period decrease");

        Button armorIncrease = new Button("Armor up");
        Button armorDecrease = new Button("Armor down");

        flow.getChildren().addAll(
                hpIncrease,
                hpDecrease,
                dodgeDamageIncrease,
                dodgeDamageDecrease,
                dodgePeriodIncrease,
                dodgePeriodDecrease,
                armorIncrease,
                armorDecrease
        );

        hpIncrease.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                tempData.curHealth.targetValue += 10;
            }
        });

        hpDecrease.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                tempData.curHealth.targetValue -= 10;
            }
        });

        dodgeDamageIncrease.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                tempData.dodgeDamage += 10;
            }
        });

        dodgeDamageDecrease.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                tempData.dodgeDamage -= 10;
            }
        });

        armorIncrease.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                tempData.curArmour += 10;
            }
        });

        armorDecrease.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                tempData.curArmour -= 10;
            }
        });

        dodgePeriodIncrease.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                tempData.dodgePeriod += 100;
            }
        });

        dodgePeriodDecrease.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                tempData.dodgePeriod -= 100;
            }
        });
    }

    private void moo() {
        new AnimationTimer() {
            public void handle(long l) {
                GraphicsContext g2d = canvas.getGraphicsContext2D();
                g2d.clearRect(0,0, WIDTH, HEIGHT);

                healthBar(g2d);

                dodgeDamage(g2d);

                armorBar(g2d);

                finalBorder(g2d);

                tween.run();

                tempData.curHealth.run();

                g2d.fillOval(200, tween.curValue - 10, 20, 20);
            }
        }.start();
    }

    private double hpChange = 0;

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
            if(hpChange <= 0.02) {
                hpChange = 1;
            } else {
                hpChange -= 0.01;
            }

            g2d.setFill(new Color(0.1,1 * hpChange,1 - (1*hpChange), 1));

            // Health bar
            g2d.fillRect(
                    HEALTH_X,
                    HEALTH_Y + (HEALTH_HEIGHT - (((double)Math.max(tempData.curHealth.curValue, tempData.curHealth.targetValue) / (double)tempData.maxHealth) * HEALTH_HEIGHT)),
                    HEALTH_WIDTH,
                    (((double)Math.abs(tempData.curHealth.curValue-tempData.curHealth.targetValue) / (double)tempData.maxHealth) * HEALTH_HEIGHT)
            );
        } else {
            hpChange = 0;
        }
    }

    private void dodgeDamage(GraphicsContext g2d) {
        g2d.setFill(new Color(0.8,0.1,0.2, 0.7));

        double dodgeDamageStartY = HEALTH_Y + (HEALTH_HEIGHT - ((((double)tempData.curHealth.curValue) / (double)tempData.maxHealth) * HEALTH_HEIGHT));
        double dodgeDamageHeight = ((((double)tempData.dodgeDamage) / (double)tempData.maxHealth) * HEALTH_HEIGHT);

        g2d.fillRect(
                HEALTH_X + (HEALTH_WIDTH / 4),
                dodgeDamageStartY,
                (double)HEALTH_WIDTH / 4 * 3,
                dodgeDamageHeight
        );

        dodgeTimeIndicator(g2d, dodgeDamageStartY, dodgeDamageHeight);
    }

    private void dodgeTimeIndicator(GraphicsContext g2d, double dodgeDamageStartY, double dodgeDamageHeight) {
        double periodRiskiness = (double)tempData.dodgePeriod / 3000;

        double inc_height = ((double)DODGE_DAMAGE_INC / tempData.dodgePeriod) * dodgeDamageHeight;

        double dodgeDamagePeriodLine = dodgeDamageStartY + inc_height;//HEALTH_Y;

        int counter = 0;

        while(dodgeDamagePeriodLine < dodgeDamageStartY + dodgeDamageHeight) {
            counter++;

            if(dodgeDamagePeriodLine < dodgeDamageStartY) {
                dodgeDamagePeriodLine += inc_height;
                continue;
            }

            if(counter % 4 == 0) {
                g2d.setStroke(new Color(1,1,1,1));
                g2d.setLineDashes(0);

                g2d.strokeLine(
                        HEALTH_X + (HEALTH_WIDTH / 4),
                        dodgeDamagePeriodLine,
                        HEALTH_X + HEALTH_WIDTH,
                        dodgeDamagePeriodLine
                );

                g2d.setStroke(new Color(1,0,0,0.6));
                g2d.setLineDashes(1,4);
            } else {
                g2d.setStroke(new Color(1,1,1,0.4));
                g2d.setLineDashes(1,4);
            }

            g2d.strokeLine(
                    HEALTH_X + (HEALTH_WIDTH / 4),
                    dodgeDamagePeriodLine,
                    HEALTH_X + HEALTH_WIDTH,
                    dodgeDamagePeriodLine
            );

            dodgeDamagePeriodLine += inc_height;
        }

        g2d.setLineDashes(0);
    }

    private void armorBar(GraphicsContext g2d) {
        g2d.setStroke(new Color(0,0,0,0.7));

        g2d.setFill(new Color(0.9F, 0.9F, 0.3F, 0.8));
        g2d.fillRect(
                HEALTH_X + ((double)HEALTH_WIDTH / 4 * 3),
                HEALTH_Y + (HEALTH_HEIGHT - (((double)tempData.curArmour / (double)tempData.maxArmour) * HEALTH_HEIGHT)),
                (double)HEALTH_WIDTH / 4,
                (((double)tempData.curArmour / (double)tempData.maxArmour) * HEALTH_HEIGHT)
        );
        g2d.strokeRect(
                HEALTH_X + ((double)HEALTH_WIDTH / 4 * 3),
                HEALTH_Y + (HEALTH_HEIGHT - (((double)tempData.curArmour / (double)tempData.maxArmour) * HEALTH_HEIGHT)),
                (double)HEALTH_WIDTH / 4,
                (((double)tempData.curArmour / (double)tempData.maxArmour) * HEALTH_HEIGHT));
    }

    private void finalBorder(GraphicsContext g2d) {
        g2d.setStroke(Color.BLACK);
        g2d.strokeRect(HEALTH_X, HEALTH_Y, HEALTH_WIDTH, HEALTH_HEIGHT);
    }
}
