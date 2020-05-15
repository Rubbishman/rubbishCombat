package com.rubbishman.rubbishcombat.actions.internalcombat;

import com.rubbishman.rubbishRedux.external.operational.store.Identifier;

public class DodgeDamageOTResolution {
    public final Identifier target;
    public final int damage;
    public DodgeDamageOTResolution(Identifier target, int damage) {
        this.target = target;
        this.damage = damage;
    }
}
