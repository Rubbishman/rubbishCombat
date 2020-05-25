package com.rubbishman.rubbishcombat.gui.components;

import com.rubbishman.rubbishcombat.gui.data.CombatText;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CombatTextRendererBar extends ICombatTextRenderer {

    public CombatTextRendererBar(CombatText combatText) {
        super(combatText);
    }

    public void draw(GraphicsContext g2d) {
        g2d.setStroke(Color.BLACK);
        g2d.setFill(Color.BLACK);

        final String msg = combatText.fullDamage + "";
        final Text text = new Text(msg);
        Font font = Font.font("Arial", 30);
        text.setFont(font);

        final double width = text.getLayoutBounds().getWidth();
        final double height = text.getLayoutBounds().getHeight();

        g2d.setFont(font);
        g2d.fillText(msg, combatText.x, combatText.y);

        g2d.strokeLine(combatText.x + width + 5, 5 + combatText.y - height, combatText.x + width + 5, combatText.y);

        double damagePer = (double)(combatText.fullDamage - combatText.dodgeDamage - combatText.armorDamage) / combatText.fullDamage;
        double armorPer = (double)combatText.armorDamage / combatText.fullDamage;
        double dodgePer = (double)combatText.dodgeDamage / combatText.fullDamage;

        final double lineHeight = height * 2;

        if(Math.random() > 0.5) {
            horizontal(g2d, damagePer, armorPer, dodgePer, width, height);
        } else {
            vertical(g2d, damagePer, armorPer, dodgePer, width, height);
        }
    }

    private void horizontal(GraphicsContext g2d, double damagePer, double armorPer, double dodgePer, double width, double height) {
        if(damagePer > 0) {
            g2d.setFill(Color.RED);
            g2d.fillRect(
                    combatText.x + width + 5,
                    5 + combatText.y - height + (height * armorPer),
                    5,
                    height * damagePer
            );
            g2d.setStroke(((Color)g2d.getFill()).darker().darker());
            g2d.strokeRect(
                    combatText.x + width + 5,
                    5 + combatText.y - height + (height * armorPer),
                    5,
                    height * damagePer
            );
        }

        if(armorPer > 0) {
            g2d.setFill(Color.YELLOW);
            g2d.fillRect(
                    combatText.x + width + 5,
                    5 + combatText.y - height,
                    5,
                    height * armorPer
            );
            g2d.setStroke(((Color)g2d.getFill()).darker().darker());
            g2d.strokeRect(
                    combatText.x + width + 5,
                    5 + combatText.y - height,
                    5,
                    height * armorPer
            );
        }

        if(dodgePer > 0) {
            g2d.setFill(Color.PURPLE);
            g2d.fillRect(
                    combatText.x + width + 5,
                    5 + combatText.y - (height * dodgePer),
                    5,
                    height * dodgePer
            );
            g2d.setStroke(((Color)g2d.getFill()).darker().darker());
            g2d.strokeRect(
                    combatText.x + width + 5,
                    5 + combatText.y - (height * dodgePer),
                    5,
                    height * dodgePer
            );
        }
    }

    private void vertical(GraphicsContext g2d, double damagePer, double armorPer, double dodgePer, double width, double height) {
        if(damagePer > 0) {
            g2d.setFill(Color.RED);
            g2d.fillRect(
                    combatText.x + width + 5,
                    5 + combatText.y - height + (height * armorPer),
                    5,
                    height * damagePer
            );
            g2d.setStroke(((Color)g2d.getFill()).darker().darker());
            g2d.strokeRect(
                    combatText.x + width + 5,
                    5 + combatText.y - height + (height * armorPer),
                    5,
                    height * damagePer
            );
        }

        if(armorPer > 0) {
            g2d.setFill(Color.YELLOW);
            g2d.fillRect(
                    combatText.x + width + 5,
                    5 + combatText.y - height,
                    5,
                    height * armorPer
            );
            g2d.setStroke(((Color)g2d.getFill()).darker().darker());
            g2d.strokeRect(
                    combatText.x + width + 5,
                    5 + combatText.y - height,
                    5,
                    height * armorPer
            );
        }

        if(dodgePer > 0) {
            g2d.setFill(Color.PURPLE);
            g2d.fillRect(
                    combatText.x + width + 5,
                    5 + combatText.y - (height * dodgePer),
                    5,
                    height * dodgePer
            );
            g2d.setStroke(((Color)g2d.getFill()).darker().darker());
            g2d.strokeRect(
                    combatText.x + width + 5,
                    5 + combatText.y - (height * dodgePer),
                    5,
                    height * dodgePer
            );
        }
    }
}
