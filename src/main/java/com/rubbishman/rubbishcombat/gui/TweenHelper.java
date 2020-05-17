package com.rubbishman.rubbishcombat.gui;

public class TweenHelper {
    public double curValue, targetValue;
    public double curVelocity, maxVelocity, accel;

    public TweenHelper(double curValue, double targetValue, double curVelocity, double maxVelocity, double accel) {
        this.curValue = curValue;

        this.targetValue = targetValue;
        this.curVelocity = curVelocity;
        this.maxVelocity = maxVelocity;
        this.accel = accel;
    }

    public void run() {
        if(curValue < targetValue) {
            curVelocity = Math.min(Math.max(curVelocity,0) + accel, maxVelocity);
        } else {
            curVelocity = Math.max(Math.min(curVelocity,0) - accel, maxVelocity * -1);
        }

        curValue += curVelocity;

        if(curValue >= targetValue && curVelocity > 0
                || curValue <= targetValue && curVelocity < 0) {
            curValue = targetValue;
            curVelocity = 0;
        }
    }
}
