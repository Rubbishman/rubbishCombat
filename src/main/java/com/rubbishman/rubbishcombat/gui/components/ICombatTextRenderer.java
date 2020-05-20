package com.rubbishman.rubbishcombat.gui.components;

import com.rubbishman.rubbishcombat.gui.data.CombatText;
import javafx.scene.canvas.GraphicsContext;

public abstract class ICombatTextRenderer {
    protected CombatText combatText;

    public ICombatTextRenderer(CombatText combatText) {
        this.combatText = combatText;
    }

    public abstract void draw(GraphicsContext g2d);

    public boolean isTerminated(long nowTime) {
        return combatText.isTerminated(nowTime);
    }

    public void run(long nowTime) {
        combatText.run(nowTime);
    }
}
