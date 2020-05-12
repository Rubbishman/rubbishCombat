package com.rubbishman.rubbishcombat.state.affects;

import com.rubbishman.rubbishRedux.external.operational.store.Identifier;

public class DodgeDamageOT {
    public final Identifier target;
    public final long startTime;
    public final long curTime;
    public final int damage;
    public final long period;
    public final double remainder;

    public DodgeDamageOT(Identifier target, long startTime, long curTime, int damage, long period) {
        this.target = target;
        this.startTime = startTime;
        this.curTime = curTime;
        this.damage = damage;
        this.period = period;
        remainder = 0;
    }

    public DodgeDamageOT(Identifier target, long startTime, long curTime, int damage, long period, double remainder) {
        this.target = target;
        this.startTime = startTime;
        this.curTime = curTime;
        this.damage = damage;
        this.period = period;
        this.remainder = remainder;
    }
}
