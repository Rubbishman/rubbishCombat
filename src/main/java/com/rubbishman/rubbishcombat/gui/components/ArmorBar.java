package com.rubbishman.rubbishcombat.gui.components;

import com.rubbishman.rubbishcombat.gui.data.TempData;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ArmorBar {
    public void armorBar(TempData tempData, GraphicsContext g2d) {
        g2d.setStroke(new Color(0,0,0,0.7));

        g2d.setFill(new Color(0.9F, 0.9F, 0.3F, 0.8));
        g2d.fillRect(
                HealthBarPane.HEALTH_X + ((double)HealthBarPane.HEALTH_WIDTH / 4 * 3),
                HealthBarPane.HEALTH_Y + (HealthBarPane.HEALTH_HEIGHT - (((double)tempData.curArmour.curValue / (double)tempData.maxArmour) * HealthBarPane.HEALTH_HEIGHT)),
                (double)HealthBarPane.HEALTH_WIDTH / 4,
                (((double)(tempData.curArmour.curValue) / (double)tempData.maxArmour) * HealthBarPane.HEALTH_HEIGHT)
        );
        g2d.strokeRect(
                HealthBarPane.HEALTH_X + ((double)HealthBarPane.HEALTH_WIDTH / 4 * 3),
                HealthBarPane.HEALTH_Y + (HealthBarPane.HEALTH_HEIGHT - (((double)tempData.curArmour.curValue / (double)tempData.maxArmour) * HealthBarPane.HEALTH_HEIGHT)),
                (double)HealthBarPane.HEALTH_WIDTH / 4,
                (((double)tempData.curArmour.curValue / (double)tempData.maxArmour) * HealthBarPane.HEALTH_HEIGHT));

        if(tempData.curArmour.curValue != tempData.curArmour.targetValue) {
            g2d.setFill(new Color(0.6,0.6,0.3, 1));

            // Health bar
            g2d.fillRect(
                    HealthBarPane.HEALTH_X + ((double)HealthBarPane.HEALTH_WIDTH / 4 * 3),
                    HealthBarPane.HEALTH_Y + (HealthBarPane.HEALTH_HEIGHT - (((double)Math.max(tempData.curArmour.curValue, tempData.curArmour.targetValue) / (double)tempData.maxArmour) * HealthBarPane.HEALTH_HEIGHT)),
                    (double)HealthBarPane.HEALTH_WIDTH / 4,
                    (((double)Math.abs(tempData.curArmour.curValue-(tempData.curArmour.targetValue - tempData.armorDamage)) / (double)tempData.maxArmour) * HealthBarPane.HEALTH_HEIGHT)
            );

            g2d.strokeRect(
                    HealthBarPane.HEALTH_X + ((double)HealthBarPane.HEALTH_WIDTH / 4 * 3),
                    HealthBarPane.HEALTH_Y + (HealthBarPane.HEALTH_HEIGHT - (((double)Math.max(tempData.curArmour.curValue, tempData.curArmour.targetValue) / (double)tempData.maxArmour) * HealthBarPane.HEALTH_HEIGHT)),
                    (double)HealthBarPane.HEALTH_WIDTH / 4,
                    (((double)Math.abs(tempData.curArmour.curValue-(tempData.curArmour.targetValue - tempData.armorDamage)) / (double)tempData.maxArmour) * HealthBarPane.HEALTH_HEIGHT));
        }

        /*double armorBitMin = Math.max(tempData.curArmour.curValue, tempData.curArmour.targetValue);

        for(int i = 0; i < armorBitMin; i++) {
            if(i % 5 == 0) {
                continue;
            }

            g2d.setStroke(new Color(0.9F, 0.9F, 0.3F, 0.8).darker());

            if(i % 10 == 0) {
                g2d.setStroke(new Color(0.9F, 0.9F, 0.3F, 0.8).darker().darker());
            }

            if(i % 25 == 0) {
                g2d.setStroke(new Color(0.9F, 0.9F, 0.3F, 0.8).darker().darker().darker());
            }

            g2d.strokeLine(
                    HealthBarPane.HEALTH_X + ((double)HealthBarPane.HEALTH_WIDTH / 4 * 3),
                    HealthBarPane.HEALTH_Y + HealthBarPane.HEALTH_HEIGHT - ((1 / (double)tempData.maxArmour) * HealthBarPane.HEALTH_HEIGHT * i),
                    HealthBarPane.HEALTH_X + ((double)HealthBarPane.HEALTH_WIDTH / 4 * 3) + (double)HealthBarPane.HEALTH_WIDTH / 4,
                    HealthBarPane.HEALTH_Y + HealthBarPane.HEALTH_HEIGHT - ((1 / (double)tempData.maxArmour) * HealthBarPane.HEALTH_HEIGHT * i)
            );
        }*/
    }
}
