package com.rubbishman.rubbishcombat.actions;

import com.rubbishman.rubbishRedux.external.operational.store.Identifier;

public class Damage {
    public final int damage;
    public final int type;
    public final Identifier target;

    public Damage(Identifier target, int damage, int type) {
        this.target = target;
        this.damage = damage;
        this.type = type;
    }
}
