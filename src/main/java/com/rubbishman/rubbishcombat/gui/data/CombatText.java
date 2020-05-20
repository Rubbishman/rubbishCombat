package com.rubbishman.rubbishcombat.gui.data;

import java.util.concurrent.TimeUnit;

public class CombatText {
    public double x, y;
    public int fullDamage, armorDamage, dodgeDamage;

    public long created = System.nanoTime();
    public long duration = TimeUnit.NANOSECONDS.convert(2, TimeUnit.SECONDS);

    public CombatText(double x, double y, int fullDamage, int armorDamage, int dodgeDamage) {
        this.x = x;
        this.y = y;
        this.fullDamage = fullDamage;
        this.armorDamage = armorDamage;
        this.dodgeDamage = dodgeDamage;

    }

    public boolean isTerminated(long nowTime) {
        return created + duration < nowTime;
    }

    public void run(long nowTime) {
        y -= 1;
    }
}
