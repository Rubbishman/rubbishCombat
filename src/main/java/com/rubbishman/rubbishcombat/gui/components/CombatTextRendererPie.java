package com.rubbishman.rubbishcombat.gui.components;

import com.rubbishman.rubbishcombat.gui.data.CombatText;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CombatTextRendererPie extends ICombatTextRenderer {

    public CombatTextRendererPie(CombatText combatText) {
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

        double damagePer = (double)(combatText.fullDamage - combatText.dodgeDamage - combatText.armorDamage) / combatText.fullDamage;
        double armorPer = (double)combatText.armorDamage / combatText.fullDamage;
        double dodgePer = (double)combatText.dodgeDamage / combatText.fullDamage;

        final double lineHeight = height * 2;

        if(damagePer > 0) {
            g2d.setFill(Color.RED);

            g2d.fillArc(
                    combatText.x + width,
                    combatText.y,
                    15,
                    15,
                    0,
                    360 * damagePer,
                    ArcType.ROUND);
        }

        if(armorPer > 0) {
            g2d.setFill(Color.YELLOW);

            g2d.fillArc(
                    combatText.x + width,
                    combatText.y,
                    15,
                    15,
                    360 * damagePer,
                    360 * armorPer,
                    ArcType.ROUND);
        }

        if(dodgePer > 0) {
            g2d.setFill(Color.CADETBLUE);
            g2d.fillArc(
                    combatText.x + width,
                    combatText.y,
                    15,
                    15,
                    360 * damagePer + 360 * armorPer,
                    360 * dodgePer,
                    ArcType.ROUND);
        }

        g2d.strokeArc(
                combatText.x + width,
                combatText.y,
                15,
                15,
                0,
                360,
                ArcType.OPEN);
    }
}
