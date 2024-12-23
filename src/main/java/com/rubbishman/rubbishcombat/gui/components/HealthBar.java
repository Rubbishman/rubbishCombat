package com.rubbishman.rubbishcombat.gui.components;

import com.rubbishman.rubbishcombat.gui.data.TempData;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class HealthBar {
    public void healthBar(TempData tempData, GraphicsContext g2d) {
        g2d.setFill(new Color(0.1,0.7,0.3, 1));

        // Health bar
        g2d.fillRect(
                HealthBarPane.HEALTH_X,
                HealthBarPane.HEALTH_Y + (HealthBarPane.HEALTH_HEIGHT - (((double)tempData.curHealth.curValue / (double)tempData.maxHealth) * HealthBarPane.HEALTH_HEIGHT)),
                HealthBarPane.HEALTH_WIDTH,
                (((double)tempData.curHealth.curValue / (double)tempData.maxHealth) * HealthBarPane.HEALTH_HEIGHT)
        );

        if(tempData.curHealth.curValue != tempData.curHealth.targetValue) {
            g2d.setFill(new Color(0.118,0.408,0.047, 1));

            // Health bar
            g2d.fillRect(
                    HealthBarPane.HEALTH_X,
                    HealthBarPane.HEALTH_Y + (HealthBarPane.HEALTH_HEIGHT - (((double)Math.max(tempData.curHealth.curValue, tempData.curHealth.targetValue) / (double)tempData.maxHealth) * HealthBarPane.HEALTH_HEIGHT)),
                    HealthBarPane.HEALTH_WIDTH,
                    (((double)Math.abs(tempData.curHealth.curValue-tempData.curHealth.targetValue) / (double)tempData.maxHealth) * HealthBarPane.HEALTH_HEIGHT)
            );
        }

        double lifeBitMin = Math.max(tempData.curHealth.curValue, tempData.curHealth.targetValue);

        for(int i = 0; i < lifeBitMin; i++) {
            g2d.setStroke(new Color(0.1,0.5,0.2, 1));

            if(i % 10 == 0) {
                g2d.setStroke(new Color(0.1,0.4,0.2, 1));
            }

            if(i % 25 == 0) {
                g2d.setStroke(new Color(0.1,0.1,0.2, 1));
            }

            g2d.strokeLine(
                    HealthBarPane.HEALTH_X,
                    HealthBarPane.HEALTH_Y + HealthBarPane.HEALTH_HEIGHT - ((1 / (double)tempData.maxHealth) * HealthBarPane.HEALTH_HEIGHT * i),
                    HealthBarPane.HEALTH_X + HealthBarPane.HEALTH_WIDTH,
                    HealthBarPane.HEALTH_Y + HealthBarPane.HEALTH_HEIGHT - ((1 / (double)tempData.maxHealth) * HealthBarPane.HEALTH_HEIGHT * i)
            );
        }
    }

    public void finalBorder(GraphicsContext g2d) {
        g2d.setStroke(Color.BLACK);
        g2d.strokeRect(HealthBarPane.HEALTH_X, HealthBarPane.HEALTH_Y, HealthBarPane.HEALTH_WIDTH, HealthBarPane.HEALTH_HEIGHT);
    }
}
