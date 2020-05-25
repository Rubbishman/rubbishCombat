package com.rubbishman.rubbishcombat.gui.components;

import com.rubbishman.rubbishcombat.gui.data.TempData;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DodgeBar {
    public void dodgeDamage(TempData tempData, GraphicsContext g2d) {
        g2d.setFill(new Color(0.8,0.1,0.2, 0.5));

        double dodgeDamageStartY = HealthBarPane.HEALTH_Y + (HealthBarPane.HEALTH_HEIGHT - ((((double)tempData.curHealth.targetValue) / (double)tempData.maxHealth) * HealthBarPane.HEALTH_HEIGHT));
        double dodgeDamageHeight = ((((double)tempData.dodgeDamage) / (double)tempData.maxHealth) * HealthBarPane.HEALTH_HEIGHT);

        g2d.fillRect(
                HealthBarPane.HEALTH_X + (HealthBarPane.HEALTH_WIDTH / 8),
                dodgeDamageStartY,
                (double)HealthBarPane.HEALTH_WIDTH / 8 * 7,
                dodgeDamageHeight
        );

//        g2d.setStroke(new Color(0.8,0.1,0.2, 0.5));
//
//        g2d.strokeRect(
//                HealthBarPane.HEALTH_X + (HealthBarPane.HEALTH_WIDTH / 5),
//                dodgeDamageStartY,
//                (double)HealthBarPane.HEALTH_WIDTH / 5 * 4,
//                dodgeDamageHeight
//        );
    }
}
